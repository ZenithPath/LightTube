package com.example.scame.lighttube.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.entities.search.CategoryPairs;
import com.example.scame.lighttube.data.mappers.CategoryPairsMapper;
import com.example.scame.lighttube.data.mappers.SearchListMapper;
import com.example.scame.lighttube.data.rest.SearchApi;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import java.util.List;
import java.util.Map;

import rx.Observable;

public class CategoryRepositoryImp implements CategoryRepository {

    // TODO: implement dynamic regions
    private static final String REGION_CODE = "US";

    private static final String PART = "snippet";

    private SearchApi searchApi;

    private CategoryPairsMapper pairsMapper;

    private SearchRepository searchDataManager;

    private SearchListMapper searchListMapper;

    private List<String> categoryKeys;

    private SharedPreferences sharedPrefs;

    private Context context;

    public CategoryRepositoryImp(SharedPreferences sharedPrefs, Context context, SearchApi searchApi,
                                 CategoryPairsMapper pairsMapper, List<String> categoryKeys,
                                 SearchRepository searchDataManager, SearchListMapper searchListMapper) {
        this.sharedPrefs = sharedPrefs;
        this.context = context;

        this.searchApi = searchApi;
        this.pairsMapper = pairsMapper;
        this.searchDataManager = searchDataManager;
        this.searchListMapper = searchListMapper;

        this.categoryKeys = categoryKeys;
    }

    @Override
    public Observable<VideoModelsWrapper> getVideosByCategory(String category, String duration, int page) {
        return getCategoryId(category)
                .flatMap(categoryId -> searchDataManager.searchByCategory(categoryId, duration, page))
                .map(searchEntity -> searchListMapper.convert(searchEntity, page));
    }

    private Observable<String> getCategoryId(String category) {
        return getCategoriesIds(REGION_CODE)
                .filter(entry -> entry.getKey().equals(category))
                .map(Map.Entry::getValue);
    }

    private Observable<Map.Entry<String, String>> getCategoriesIds(String regionCode) {
        return isCategoriesCached() ? getFromLocalStorage() : getFromRemote(regionCode);
    }

    private boolean isCategoriesCached() {
        return sharedPrefs.getBoolean(context.getString(R.string.is_categories_cached), false);
    }

    private Observable<Map.Entry<String, String>> getFromLocalStorage() {
        CategoryPairs categoryIdsMap = new CategoryPairs();

        for (String categoryKey : categoryKeys) {
            String cachedValue = sharedPrefs.getString(categoryKey, "");
            categoryIdsMap.put(categoryKey, cachedValue);
        }
        return Observable.from(categoryIdsMap);
    }

    private Observable<Map.Entry<String, String>> getFromRemote(String regionCode) {
        return searchApi.getCategoriesId(PART, regionCode, PrivateValues.API_KEY)
                .map(pairsMapper::convert)
                .flatMap(Observable::from)
                .filter(this::filterCategoryId)
                .doOnNext(this::saveCategoryId)
                .doOnCompleted(this::markAsCached);
    }

    private boolean filterCategoryId(Map.Entry<String, String> mapEntry) {
        return categoryKeys.contains(mapEntry.getKey());
    }

    private void saveCategoryId(Map.Entry<String, String> mapEntry) {
        sharedPrefs.edit().putString(mapEntry.getKey(), mapEntry.getValue()).apply();
    }

    private void markAsCached() {
        sharedPrefs.edit().putBoolean(context.getString(R.string.is_categories_cached), true).apply();
    }
}
