package com.example.scame.lighttube.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.entities.search.AutocompleteEntity;
import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.mappers.JsonDeserializer;
import com.example.scame.lighttube.data.rest.SearchApi;
import com.example.scame.lighttube.presentation.LightTubeApp;

import retrofit2.Retrofit;
import rx.Observable;

public class SearchDataManagerImp implements ISearchDataManager {

    private Retrofit retrofit;
    private SearchApi searchApi;

    private static final String CLIENT = "firefox";
    private static final String RESTRICT_TO = "yt";
    private static final String LANGUAGE = "en";

    private static final String PART = "snippet";
    private static final int MAX_RESULTS = 15;

    private static final String TYPE = "video";

    public SearchDataManagerImp() {
        retrofit = LightTubeApp.getAppComponent().getRetrofit();
        searchApi = retrofit.create(SearchApi.class);
    }

    @Override
    public Observable<SearchEntity> searchByCategory(String categoryId, String duration) {

        return searchApi.searchVideoWithCategory(PART, categoryId, matchDuration(duration),
                TYPE, MAX_RESULTS, null, PrivateValues.API_KEY);
    }

    @Override
    public Observable<AutocompleteEntity> autocomplete(String query) {

        JsonDeserializer deserializer = new JsonDeserializer();

        return searchApi.autocomplete(query, CLIENT, RESTRICT_TO, LANGUAGE)
                .map(deserializer::convert);
    }


    @Override
    public Observable<SearchEntity> search(String query, int page) {

        return searchApi.searchVideo(PART, query, MAX_RESULTS, getNextPageToken(page), PrivateValues.API_KEY)
                .doOnNext(searchEntity -> {
                    saveNextPageToken(searchEntity.getNextPageToken());
                    savePageNumber(page);
                });
    }

    private String getNextPageToken(int page) {
        String nextPageToken = getSharedPrefs()
                .getString(getContext().getString(R.string.next_page_token_search), null);
        int prevPageNumber = getSharedPrefs()
                .getInt(getContext().getString(R.string.search_page_number), 0);

        return page < prevPageNumber ? null : nextPageToken;
    }

    private void saveNextPageToken(String token) {
        getSharedPrefs().edit()
                .putString(getContext().getString(R.string.next_page_token_search), token)
                .apply();
    }

    private void savePageNumber(int page) {
        getSharedPrefs().edit().putInt(getContext().getString(R.string.search_page_number), page).apply();
    }

    private SharedPreferences getSharedPrefs() {
        Context context = LightTubeApp.getAppComponent().getApp();
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private Context getContext() {
        return LightTubeApp.getAppComponent().getApp();
    }

    private String matchDuration(String duration) {

        if (duration.equals(getContext().getString(R.string.any_duration))) {
            return "any";
        } else if (duration.equals(getContext().getString(R.string.short_duration))) {
            return "short";
        } else if (duration.equals(getContext().getString(R.string.medium_duration))) {
            return "medium";
        } else if (duration.equals(getContext().getString(R.string.long_duration))) {
            return "long";
        }

        return null;
    }
}
