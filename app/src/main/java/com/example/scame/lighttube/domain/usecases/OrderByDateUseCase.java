package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.repository.IRecentVideosDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public class OrderByDateUseCase extends UseCase<List<VideoModel>> {

    private IRecentVideosDataManager dataManager;

    private List<SearchEntity> searchEntities;

    public OrderByDateUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, IRecentVideosDataManager dataManager) {
        super(subscribeOn, observeOn);

        this.dataManager = dataManager;
    }

    @Override
    protected Observable<List<VideoModel>> getUseCaseObservable() {
        return dataManager.getOrderedVideoModels(searchEntities);
    }

    public void setSearchEntities(List<SearchEntity> searchEntities) {
        this.searchEntities = searchEntities;
    }
}
