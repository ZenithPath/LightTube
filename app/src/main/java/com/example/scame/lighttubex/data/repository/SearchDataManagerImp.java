package com.example.scame.lighttubex.data.repository;

import com.example.scame.lighttubex.PrivateValues;
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
    public Observable<SearchEntity> search(String query) {
        retrofit = LightTubeApp.getAppComponent().getRetrofit();
        SearchApi searchApi = retrofit.create(SearchApi.class);

        return searchApi.searchVideo("snippet", query, PrivateValues.API_KEY);
    }
}
