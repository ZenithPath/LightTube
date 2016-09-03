package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionsEntity;
import com.example.scame.lighttube.presentation.model.SearchItemModel;

import java.util.List;

import rx.Observable;

public interface IRecentVideosDataManager {

    Observable<SubscriptionsEntity> getSubscriptions();

    Observable<SearchEntity> getChannelsVideosByDate(String channelId);

    Observable<List<SearchItemModel>> getOrderedSearchItems(List<SearchEntity> searchEntities);
}
