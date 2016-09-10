package com.example.scame.lighttube.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.rest.RecentVideosApi;
import com.example.scame.lighttube.presentation.LightTubeApp;

import retrofit2.Retrofit;
import rx.Observable;

public class ChannelVideosDataManagerImp implements IChannelVideosDataManager {

    private static final String PART = "snippet";
    private static final int MAX_RESULTS_SEARCH = 5;
    private static final String ORDER = "date";
    private static final String TYPE = "video";

    private RecentVideosApi recentVideosApi;

    public ChannelVideosDataManagerImp() {
        Retrofit retrofit = LightTubeApp.getAppComponent().getRetrofit();
        recentVideosApi = retrofit.create(RecentVideosApi.class);
    }

    @Override
    public Observable<SearchEntity> getChannelVideos(String channelId, int page) {
        String nextPageToken = getNextPageToken(page);

        return recentVideosApi.getRecentVideos(PART, MAX_RESULTS_SEARCH, channelId, ORDER, nextPageToken, TYPE)
                .doOnNext(searchEntity -> {
                        saveNextPageToken(searchEntity);
                        savePageNumber(page);
                });
    }

    private void saveNextPageToken(SearchEntity searchEntity) {
        getSharedPrefs().edit()
                .putString(getContext().getString(R.string.next_page_token_list),
                        searchEntity.getNextPageToken()).apply();
    }


    private String getNextPageToken(int page) {
        String nextPageToken = getSharedPrefs()
                .getString(getContext().getString(R.string.next_page_token_list), null);
        int prevPageNumber = getSharedPrefs()
                .getInt(getContext().getString(R.string.list_page_number), 0);

        return page < prevPageNumber ? null : nextPageToken;
    }

    private void savePageNumber(int page) {
        getSharedPrefs().edit().putInt(getContext().getString(R.string.list_page_number), page).apply();
    }

    private SharedPreferences getSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    private Context getContext() {
        return LightTubeApp.getAppComponent().getApp();
    }
}
