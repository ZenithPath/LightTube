package com.example.scame.lighttubex.domain.usecases;


import com.example.scame.lighttubex.data.entities.search.SearchEntity;
import com.example.scame.lighttubex.data.repository.ISearchDataManager;
import com.example.scame.lighttubex.domain.schedulers.ObserveOn;
import com.example.scame.lighttubex.domain.schedulers.SubscribeOn;

import rx.Observable;

public class SearchUseCase extends UseCase<SearchEntity> {

    private ISearchDataManager dataManager;

    private String query;

    public SearchUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                         ISearchDataManager dataManager) {

        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<SearchEntity> getUseCaseObservable() {
        return dataManager.search(query);
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
