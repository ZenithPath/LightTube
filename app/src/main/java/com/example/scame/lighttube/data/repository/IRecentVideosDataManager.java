package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionsEntity;

import rx.Observable;

public interface IRecentVideosDataManager {

    Observable<SubscriptionsEntity> getSubscriptions();

    Observable<SearchEntity> getChannelsVideosByDate(String channelId);
}
