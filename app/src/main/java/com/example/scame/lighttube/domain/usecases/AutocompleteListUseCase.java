package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.entities.search.AutocompleteEntity;
import com.example.scame.lighttube.data.repository.ISearchDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import java.util.List;

import rx.Observable;

public class AutocompleteListUseCase extends UseCase<List<String>> {

    private ISearchDataManager dataManager;

    private String query;

    public AutocompleteListUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                   ISearchDataManager dataManager) {

        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<List<String>> getUseCaseObservable() {
        return dataManager.autocomplete(query).map(AutocompleteEntity::getItems);
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
