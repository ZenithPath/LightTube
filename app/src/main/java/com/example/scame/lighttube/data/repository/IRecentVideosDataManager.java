package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionsEntity;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public interface IRecentVideosDataManager {

    void saveSubscriptionsNumber(int subscriptions);

    Observable<SubscriptionsEntity> getSubscriptions();

    Observable<SearchEntity> getChannelsVideosByDate(String channelId);

    Observable<List<VideoModel>> getOrderedVideoModels(List<SearchEntity> searchEntities);
}
