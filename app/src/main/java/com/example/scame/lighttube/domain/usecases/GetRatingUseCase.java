package com.example.scame.lighttube.domain.usecases;

import com.example.scame.lighttube.data.repository.RatingRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.RatingModel;

import rx.Observable;

public class GetRatingUseCase extends UseCase<RatingModel> {

    private RatingRepository dataManager;

    private String videoId;

    public GetRatingUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, RatingRepository dataManager) {
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

    public String getVideoId() {
        return videoId;
    }
}
