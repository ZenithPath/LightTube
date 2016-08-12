package com.example.scame.lighttubex.domain.usecases;


import com.example.scame.lighttubex.data.mappers.SearchListMapper;
import com.example.scame.lighttubex.data.repository.ISearchDataManager;
import com.example.scame.lighttubex.domain.schedulers.ObserveOn;
import com.example.scame.lighttubex.domain.schedulers.SubscribeOn;
import com.example.scame.lighttubex.presentation.model.SearchItemModel;

import java.util.List;

import rx.Observable;

public class SearchUseCase extends UseCase<List<SearchItemModel>> {

    private ISearchDataManager dataManager;

    private String query;

    private int page;

    public SearchUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                         ISearchDataManager dataManager) {

        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<List<SearchItemModel>> getUseCaseObservable() {
        SearchListMapper mapper = new SearchListMapper(); // add to dagger

        return dataManager.search(query, page).map(mapper::convert);
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
