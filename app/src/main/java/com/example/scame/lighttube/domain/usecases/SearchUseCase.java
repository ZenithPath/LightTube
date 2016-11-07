package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.SearchRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import rx.Observable;

public class SearchUseCase extends UseCase<VideoModelsWrapper> {

    private SearchRepository searchDataManager;

    private String query;

    private int page;

    public SearchUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                         SearchRepository searchDataManager) {
        super(subscribeOn, observeOn);
        this.searchDataManager = searchDataManager;
    }

    @Override
    protected Observable<VideoModelsWrapper> getUseCaseObservable() {
        return searchDataManager.search(query, page);
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getQuery() {
        return query;
    }

    public int getPage() {
        return page;
    }
}
