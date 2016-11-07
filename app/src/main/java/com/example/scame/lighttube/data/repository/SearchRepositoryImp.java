package com.example.scame.lighttube.data.repository;

import android.content.Context;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.entities.search.AutocompleteEntity;
import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.mappers.AutocompleteDeserializer;
import com.example.scame.lighttube.data.mappers.SearchListMapper;
import com.example.scame.lighttube.data.rest.SearchApi;
import com.example.scame.lighttube.presentation.LightTubeApp;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import javax.inject.Inject;

import rx.Observable;

public class SearchRepositoryImp implements SearchRepository {

    private static final String CLIENT = "firefox";
    private static final String RESTRICT_TO = "yt";
    private static final String LANGUAGE = "en";

    private static final String PART = "snippet";
    private static final int MAX_RESULTS = 15;

    private static final String TYPE = "video";

    @Inject
    PaginationUtility paginationUtility;

    private ContentDetailsRepository detailsDataManager;

    private AutocompleteDeserializer deserializer;

    private SearchListMapper searchListMapper;

    private SearchApi searchApi;

    private Context context;

    public SearchRepositoryImp(SearchApi searchApi, SearchListMapper searchListMapper,
                               AutocompleteDeserializer deserializer, Context context,
                               ContentDetailsRepository detailsDataManager) {
        this.detailsDataManager = detailsDataManager;
        this.searchListMapper = searchListMapper;
        this.deserializer = deserializer;
        this.searchApi = searchApi;
        this.context = context;

        LightTubeApp.getAppComponent().inject(this);
        paginationUtility.setPageStringId(R.string.page_number_general);
        paginationUtility.setTokenStringId(R.string.next_page_general);
    }

    @Override
    public Observable<SearchEntity> searchByCategory(String categoryId, String duration, int page) {
        return searchApi.searchVideoWithCategory(PART, categoryId, matchDuration(duration),
                TYPE, MAX_RESULTS, paginationUtility.getNextPageToken(page), PrivateValues.API_KEY)
                .doOnNext(searchEntity -> {
                    paginationUtility.saveNextPageToken(searchEntity.getNextPageToken());
                    paginationUtility.saveCurrentPage(page);
                });
    }

    @Override
    public Observable<AutocompleteEntity> autocomplete(String query) {
        return searchApi.autocomplete(query, CLIENT, RESTRICT_TO, LANGUAGE).map(deserializer::convert);
    }

    @Override
    public Observable<VideoModelsWrapper> search(String query, int page) {
        return searchApi.searchVideo(PART, TYPE, query, MAX_RESULTS,
                paginationUtility.getNextPageToken(page), PrivateValues.API_KEY)
                .doOnNext(searchEntity -> {
                    paginationUtility.saveNextPageToken(searchEntity.getNextPageToken());
                    paginationUtility.saveCurrentPage(page);
                })
                .map(searchEntity -> searchListMapper.convert(searchEntity, page))
                .flatMap(detailsDataManager::getContentDetails);
    }

    private String matchDuration(String duration) {
        if (duration.equals(context.getString(R.string.any_duration))) {
            return "any";
        } else if (duration.equals(context.getString(R.string.short_duration))) {
            return "short";
        } else if (duration.equals(context.getString(R.string.medium_duration))) {
            return "medium";
        } else if (duration.equals(context.getString(R.string.long_duration))) {
            return "long";
        }
        return null;
    }
}
