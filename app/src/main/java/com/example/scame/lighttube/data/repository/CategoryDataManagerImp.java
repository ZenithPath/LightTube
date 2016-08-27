package com.example.scame.lighttube.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.entities.search.CategoryPairs;
import com.example.scame.lighttube.data.mappers.CategoryPairsMapper;
import com.example.scame.lighttube.data.rest.SearchApi;
import com.example.scame.lighttube.presentation.LightTubeApp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;

public class CategoryDataManagerImp implements ICategoriesDataManager {

    // TODO: implement dynamic regions
    private static final String REGION_CODE = "US";

    private static final String PART = "snippet";

    private List<String> categoryKeys;

    private SharedPreferences sharedPrefs;

    private Context context;

    public CategoryDataManagerImp() {
        context = LightTubeApp.getAppComponent().getApp();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        categoryKeys = Arrays.asList(context.getResources().getStringArray(R.array.category_items));
    }

    @Override
    public Observable<String> getCategoryId(String category) {
        return getCategoriesIds(REGION_CODE)
                .filter(entry -> entry.getKey().equals(category))
                .map(Map.Entry::getValue);
    }


    private Observable<Map.Entry<String, String>> getCategoriesIds(String regionCode) {

        if (sharedPrefs.getString(context.getString(R.string.is_categories_cached), "").equals("true")) {
            return getFromLocalStorage();
        }

        return getFromRemote(regionCode);
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
        Retrofit retrofit = LightTubeApp.getAppComponent().getRetrofit();
        SearchApi searchApi = retrofit.create(SearchApi.class);
        CategoryPairsMapper mapper = new CategoryPairsMapper();

        return searchApi.getCategoriesId(PART, regionCode, PrivateValues.API_KEY)
                .map(mapper::convert)
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
        sharedPrefs.edit().putString(context.getString(R.string.is_categories_cached), "true").apply();
    }
}
