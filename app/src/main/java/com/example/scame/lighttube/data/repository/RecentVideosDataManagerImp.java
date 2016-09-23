package com.example.scame.lighttube.data.repository;


import android.content.SharedPreferences;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionsEntity;
import com.example.scame.lighttube.data.mappers.PublishingDateParser;
import com.example.scame.lighttube.data.mappers.RecentVideosMapper;
import com.example.scame.lighttube.data.rest.RecentVideosApi;
import com.example.scame.lighttube.domain.usecases.SubscriptionsUseCase;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.Collections;
import java.util.List;

import rx.Observable;

public class RecentVideosDataManagerImp implements IRecentVideosDataManager {

    private static final int DEFAULT_SUBSCRIPTIONS_NUMBER = 10;
    private static final int MAX_IDS_NUMBER = 50;

    private static final String PART = "snippet";
    private static final int MAX_RESULTS_SUBS = 50;
    private static final boolean MINE = true;

    private static final String ORDER = "date";
    private static final String TYPE = "video";

    private RecentVideosApi recentVideosApi;
    private RecentVideosMapper recentVideosMapper;

    private PublishingDateParser publishingDateParser;

    private SharedPreferences sharedPrefs;

    public RecentVideosDataManagerImp(RecentVideosApi recentVideosApi, RecentVideosMapper recentVideosMapper,
                                      PublishingDateParser publishingDateParser, SharedPreferences sharedPrefs) {

        this.recentVideosApi = recentVideosApi;
        this.recentVideosMapper = recentVideosMapper;
        this.publishingDateParser = publishingDateParser;
        this.sharedPrefs = sharedPrefs;
    }

    @Override
    public Observable<SubscriptionsEntity> getSubscriptions() {
        return recentVideosApi.getSubscriptions(PART, MAX_RESULTS_SUBS, MINE, PrivateValues.API_KEY)
                .doOnNext(subscriptionsEntity -> {
                    saveSubscriptionsNumber(subscriptionsEntity.getItems().size());
                });
    }

    @Override
    public Observable<SearchEntity> getChannelsVideosByDate(String channelId) {
        return recentVideosApi.getRecentVideos(PART, computeMaxSearchResults(), channelId, ORDER, null, TYPE);
    }

    @Override
    public Observable<List<VideoModel>> getOrderedVideoModels(List<SearchEntity> searchEntities) {

        return Observable.just(searchEntities)
                .map(recentVideosMapper::convert) // convert to video models list
                .map(publishingDateParser::parse) // parse publishedAt strings & set parsed date fields
                .map(videoModels -> {
                    Collections.sort(videoModels, Collections.reverseOrder()); // sort video items by date
                    return videoModels;
                });
    }

    // YouTube Data API doesn't allow to include more than 50 ids in one request
    private int computeMaxSearchResults() {
        int subsNumber = sharedPrefs.getInt(SubscriptionsUseCase.class.getCanonicalName(), DEFAULT_SUBSCRIPTIONS_NUMBER);

        return MAX_IDS_NUMBER / subsNumber;
    }

    @Override
    public void saveSubscriptionsNumber(int subscriptions) {
        sharedPrefs.edit().putInt(SubscriptionsUseCase.class.getCanonicalName(), subscriptions).apply();
    }
}
