package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.IStatisticsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.VideoStatsModel;

import rx.Observable;

public class VideoStatsUseCase extends UseCase<VideoStatsModel> {

    private IStatisticsDataManager statsDataManager;

    private String videoId;

    public VideoStatsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, IStatisticsDataManager statsDataManager) {
        super(subscribeOn, observeOn);
        this.statsDataManager = statsDataManager;
    }

    @Override
    protected Observable<VideoStatsModel> getUseCaseObservable() {
        return statsDataManager.getVideoStatistics(videoId);
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
