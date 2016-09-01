package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.mappers.SubscriptionsIdsMapper;
import com.example.scame.lighttube.data.repository.IRecentVideosDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import java.util.List;

import rx.Observable;

public class SubscriptionsUseCase extends UseCase<List<String>> {

    private IRecentVideosDataManager recentVideosDataManager;

    public SubscriptionsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                IRecentVideosDataManager recentVideosDataManager) {

        super(subscribeOn, observeOn);
        this.recentVideosDataManager = recentVideosDataManager;
    }

    @Override
    protected Observable<List<String>> getUseCaseObservable() {
        SubscriptionsIdsMapper mapper = new SubscriptionsIdsMapper();

        return recentVideosDataManager.getSubscriptions()
                .map(mapper::convert);
    }
}
