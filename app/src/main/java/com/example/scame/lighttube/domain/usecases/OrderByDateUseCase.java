package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.repository.IRecentVideosDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.SearchItemModel;

import java.util.List;

import rx.Observable;

public class OrderByDateUseCase extends UseCase<List<SearchItemModel>> {

    private IRecentVideosDataManager dataManager;

    private List<SearchEntity> searchEntities;

    public OrderByDateUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                              IRecentVideosDataManager dataManager) {

        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<List<SearchItemModel>> getUseCaseObservable() {
        return dataManager.getOrderedSearchItems(searchEntities);
    }

    public void setSearchEntities(List<SearchEntity> searchEntities) {
        this.searchEntities = searchEntities;
    }
}
