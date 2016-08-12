package com.example.scame.lighttubex.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.scame.lighttubex.PrivateValues;
import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.data.entities.search.AutocompleteEntity;
import com.example.scame.lighttubex.data.entities.search.SearchEntity;
import com.example.scame.lighttubex.data.mappers.JsonDeserializer;
import com.example.scame.lighttubex.data.rest.SearchApi;
import com.example.scame.lighttubex.presentation.LightTubeApp;

import retrofit2.Retrofit;
import rx.Observable;

public class SearchDataManagerImp implements ISearchDataManager {

    private Retrofit retrofit;

    // TODO: refactor with dagger

    @Override
    public Observable<AutocompleteEntity> autocomplete(String query) {
        retrofit = LightTubeApp.getAppComponent().getRetrofit();
        SearchApi searchApi = retrofit.create(SearchApi.class);
        JsonDeserializer deserializer = new JsonDeserializer();

        return searchApi.autocomplete(query, "firefox", "yt", "en")
                .map(deserializer::convert);
    }


    @Override
    public Observable<SearchEntity> search(String query, int page) {
        retrofit = LightTubeApp.getAppComponent().getRetrofit();
        SearchApi searchApi = retrofit.create(SearchApi.class);

        return searchApi.searchVideo("snippet", query, 25, getNextPageToken(page), PrivateValues.API_KEY)
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
}
