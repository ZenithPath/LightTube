package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionsEntity;
import com.example.scame.lighttube.data.rest.RecentVideosApi;
import com.example.scame.lighttube.presentation.LightTubeApp;

import retrofit2.Retrofit;
import rx.Observable;

public class RecentVideosDataManagerImp implements IRecentVideosDataManager {

    public static final String PART = "snippet";
    public static final int MAX_RESULTS_SUBS = 50;
    public static final boolean MINE = true;

    public static final int MAX_RESULTS_SEARCH = 5;
    public static final String ORDER = "date";
    public static final String TYPE = "video";

    private Retrofit retrofit;
    private RecentVideosApi recentVideosApi;

    public RecentVideosDataManagerImp() {
        retrofit = LightTubeApp.getAppComponent().getRetrofit();
        recentVideosApi = retrofit.create(RecentVideosApi.class);
    }

    @Override
    public Observable<SubscriptionsEntity> getSubscriptions() {
        return recentVideosApi.getSubscriptions(PART, MAX_RESULTS_SUBS, MINE, PrivateValues.API_KEY);
    }

    @Override
    public Observable<SearchEntity> getChannelsVideosByDate(String channelId) {
        return recentVideosApi.getRecentVideos(PART, MAX_RESULTS_SEARCH, channelId, ORDER, null, TYPE);
    }
}
