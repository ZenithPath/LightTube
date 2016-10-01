package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.IRatingDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import rx.Observable;

public class RateVideoUseCase extends UseCase<Void> {

    private IRatingDataManager dataManager;

    private String rating;

    private String videoId;

    public RateVideoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, IRatingDataManager dataManager) {
        super(subscribeOn, observeOn);

        this.dataManager = dataManager;
    }

    @Override
    protected Observable<Void> getUseCaseObservable() {
        return dataManager.rateVideo(rating, videoId);
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
