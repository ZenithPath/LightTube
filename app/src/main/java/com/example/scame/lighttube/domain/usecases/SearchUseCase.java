package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.mappers.SearchListMapper;
import com.example.scame.lighttube.data.repository.ISearchDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public class SearchUseCase extends UseCase<List<VideoModel>> {

    private ISearchDataManager dataManager;

    private String query;

    private int page;

    public SearchUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                         ISearchDataManager dataManager) {

        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<List<VideoModel>> getUseCaseObservable() {
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
