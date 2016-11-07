package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.entities.search.AutocompleteEntity;
import com.example.scame.lighttube.data.repository.SearchRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import java.util.List;

import rx.Observable;

public class GetAutocompleteListUseCase extends UseCase<List<String>> {

    private SearchRepository dataManager;

    private String query;

    public GetAutocompleteListUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, SearchRepository dataManager) {
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

    public String getQuery() {
        return query;
    }
}
