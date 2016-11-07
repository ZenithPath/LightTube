package com.example.scame.lighttube.data.repository;


import android.content.SharedPreferences;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.mappers.ChannelsMapper;
import com.example.scame.lighttube.data.mappers.PublishingDateParser;
import com.example.scame.lighttube.data.mappers.RecentVideosMapper;
import com.example.scame.lighttube.data.rest.RecentVideosApi;
import com.example.scame.lighttube.presentation.model.ChannelModel;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

// TODO: app pagination and implement dynamic allocation algorithm
public class RecentVideosRepositoryImp implements RecentVideosRepository {

    private static final int DEFAULT_SUBSCRIPTIONS_NUMBER = 10;
    private static final int MAX_IDS_NUMBER = 50;

    private static final String PART = "snippet";
    private static final int MAX_RESULTS_SUBS = 50;
    private static final boolean MINE = true;

    private static final String ORDER = "date";
    private static final String TYPE = "video";

    private ContentDetailsRepository detailsDataManager;

    private RecentVideosApi recentVideosApi;
    private RecentVideosMapper recentVideosMapper;

    private PublishingDateParser publishingDateParser;

    private ChannelsMapper channelsMapper;

    private SharedPreferences sharedPrefs;

    public RecentVideosRepositoryImp(RecentVideosApi recentVideosApi, RecentVideosMapper recentVideosMapper,
                                     PublishingDateParser publishingDateParser, SharedPreferences sharedPrefs,
                                     ChannelsMapper channelsMapper, ContentDetailsRepository detailsDataManager) {
        this.recentVideosApi = recentVideosApi;
        this.recentVideosMapper = recentVideosMapper;
        this.publishingDateParser = publishingDateParser;
        this.sharedPrefs = sharedPrefs;
        this.detailsDataManager = detailsDataManager;
        this.channelsMapper = channelsMapper;
    }

    @Override
    public Observable<List<ChannelModel>> getChannels() {
        return recentVideosApi.getSubscriptions(PART, MAX_RESULTS_SUBS, MINE, PrivateValues.API_KEY)
                .doOnNext(subscriptionsEntity -> saveSubscriptionsNumber(subscriptionsEntity.getItems().size()))
                .map(channelsMapper::convert);
    }

    @Override
    public Observable<VideoModelsWrapper> getRecentVideos() {
        return getUnsortedRecentVideos()
                .flatMap(this::getOrderedVideoModels)
                .flatMap(detailsDataManager::getContentDetails);
    }

    private Observable<VideoModelsWrapper> getUnsortedRecentVideos() {
        return getChannels().flatMap(Observable::from)
                .flatMap(channelModel -> getChannelsVideosByDate(channelModel.getChannelId()))
                .toList()
                .map(searchEntities -> recentVideosMapper.convert(searchEntities, 0));
    }

    private Observable<SearchEntity> getChannelsVideosByDate(String channelId) {
        return recentVideosApi.getRecentVideos(PART, computeMaxSearchResults(), channelId, ORDER, null, TYPE)
                .subscribeOn(Schedulers.computation());
    }

    private Observable<VideoModelsWrapper> getOrderedVideoModels(VideoModelsWrapper videoModelsWrapper) {
        return Observable.just(videoModelsWrapper)
                .map(publishingDateParser::parse) // parse publishedAt strings & set parsed date fields
                .map(videoModels -> {
                    Collections.sort(videoModels.getVideoModels(), Collections.reverseOrder()); // sort video items by date
                    return new VideoModelsWrapper(videoModels.getVideoModels(), 0);
                });
    }

    // YouTube Data API doesn't allow to include more than 50 ids in one request
    private int computeMaxSearchResults() {
        int subsNumber = sharedPrefs.getInt(getClass().getCanonicalName(), DEFAULT_SUBSCRIPTIONS_NUMBER);
        return MAX_IDS_NUMBER / subsNumber;
    }

    private void saveSubscriptionsNumber(int subscriptions) {
        sharedPrefs.edit().putInt(getClass().getCanonicalName(), subscriptions).apply();
    }
}
