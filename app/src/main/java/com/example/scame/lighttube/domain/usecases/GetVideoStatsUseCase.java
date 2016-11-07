package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.StatisticsRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.VideoStatsModel;

import rx.Observable;

public class GetVideoStatsUseCase extends UseCase<VideoStatsModel> {

    private StatisticsRepository statsDataManager;

    private String videoId;

    public GetVideoStatsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, StatisticsRepository statsDataManager) {
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

    public String getVideoId() {
        return videoId;
    }
}
