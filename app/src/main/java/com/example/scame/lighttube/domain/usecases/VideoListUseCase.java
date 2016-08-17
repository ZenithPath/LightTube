package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.IVideoListDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.VideoItemModel;

import java.util.List;

import rx.Observable;

public class VideoListUseCase extends UseCase<List<VideoItemModel>> {

    private IVideoListDataManager dataManager;

    private int page;

    public VideoListUseCase(IVideoListDataManager dataManager, SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(subscribeOn, observeOn);

        this.dataManager = dataManager;
    }

    @Override
    protected Observable<List<VideoItemModel>> getUseCaseObservable() {
        return dataManager.getVideoItemsList(page);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}
