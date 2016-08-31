package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionsEntity;
import com.example.scame.lighttube.data.repository.IRecentVideosDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import rx.Observable;

public class SubscriptionsUseCase extends UseCase<SubscriptionsEntity> {

    private IRecentVideosDataManager recentVideosDataManager;

    public SubscriptionsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                IRecentVideosDataManager recentVideosDataManager) {

        super(subscribeOn, observeOn);
        this.recentVideosDataManager = recentVideosDataManager;
    }

    @Override
    protected Observable<SubscriptionsEntity> getUseCaseObservable() {
        return recentVideosDataManager.getSubscriptions();
    }
}
