package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.RatingModel;

import rx.Observable;

public interface IRatingDataManager {

    Observable<Void> rateVideo(String rating, String videoId);

    Observable<RatingModel> getVideoRating(String videoId);
}
