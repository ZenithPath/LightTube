package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.repository.IRecentVideosDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import rx.Observable;
import rx.Subscriber;

public class RecentVideosUseCase extends UseCase<SearchEntity> {

    private IRecentVideosDataManager dataManager;

    private String channelId;

    public RecentVideosUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                               IRecentVideosDataManager dataManager) {

        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<SearchEntity> getUseCaseObservable() {
        return dataManager.getChannelsVideosByDate(channelId);
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public void execute(Subscriber<SearchEntity> subscriber) {

        Observable<SearchEntity> observable = getUseCaseObservable()
                .subscribeOn(subscribeOn.getScheduler())
                .observeOn(observeOn.getScheduler())
                .cache();

        observable.subscribe(subscriber);
    }
}
