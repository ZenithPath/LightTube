package com.example.scame.lighttube.data.rest;


import com.example.scame.lighttube.data.entities.rating.RatingEntity;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface RatingApi {

    @GET("youtube/v3/videos/getRating")
    Observable<RatingEntity> getVideoRating(@Query("id") String id, @Query("key") String key);

    @POST("youtube/v3/videos/rate")
    Observable<Void> rateVideo(@Query("id") String id, @Query("rating") String rating, @Query("key") String key);
}
