package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.RecentVideosRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import rx.Observable;

public class GetRecentVideosUseCase extends UseCase<VideoModelsWrapper> {

    private RecentVideosRepository videosDataManager;

    public GetRecentVideosUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                  RecentVideosRepository videosDataManager) {
        super(subscribeOn, observeOn);
        this.videosDataManager = videosDataManager;
    }

    @Override
    protected Observable<VideoModelsWrapper> getUseCaseObservable() {
        return videosDataManager.getRecentVideos();
    }
}
