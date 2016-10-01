package com.example.scame.lighttube.domain.usecases;

import com.example.scame.lighttube.data.repository.IRatingDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.RatingModel;

import rx.Observable;

public class RetrieveRatingUseCase extends UseCase<RatingModel> {

    private IRatingDataManager dataManager;

    private String videoId;

    public RetrieveRatingUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, IRatingDataManager dataManager) {
        super(subscribeOn, observeOn);

        this.dataManager = dataManager;
    }

    @Override
    protected Observable<RatingModel> getUseCaseObservable() {
        return dataManager.getVideoRating(videoId);
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
