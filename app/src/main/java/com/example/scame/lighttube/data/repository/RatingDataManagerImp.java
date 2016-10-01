package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.mappers.RatingMapper;
import com.example.scame.lighttube.data.rest.RatingApi;
import com.example.scame.lighttube.presentation.model.RatingModel;

import rx.Observable;

public class RatingDataManagerImp implements IRatingDataManager {

    private RatingApi ratingApi;

    private RatingMapper ratingMapper;

    public RatingDataManagerImp(RatingApi ratingApi, RatingMapper ratingMapper) {
        this.ratingApi = ratingApi;
        this.ratingMapper = ratingMapper;
    }

    @Override
    public Observable<Void> rateVideo(String rating, String videoId) {
        return ratingApi.rateVideo(videoId, rating, PrivateValues.API_KEY);
    }

    @Override
    public Observable<RatingModel> getVideoRating(String videoId) {
        return ratingApi.getVideoRating(videoId, PrivateValues.API_KEY)
                .map(ratingMapper::convert);
    }
}
