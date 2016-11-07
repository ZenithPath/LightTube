package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.HomeVideosRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import rx.Observable;

public class GetHomeVideosUseCase extends UseCase<VideoModelsWrapper> {

    private HomeVideosRepository dataManager;

    private int page;

    public GetHomeVideosUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                HomeVideosRepository dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<VideoModelsWrapper> getUseCaseObservable() {
        return dataManager.getVideoItemsList(page);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}
