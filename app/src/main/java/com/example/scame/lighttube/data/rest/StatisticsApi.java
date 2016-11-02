package com.example.scame.lighttube.data.rest;


import com.example.scame.lighttube.data.entities.statistics.VideoStatisticsEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface StatisticsApi {

    @GET("youtube/v3/videos")
    Observable<VideoStatisticsEntity> getVideoStatistics(@Query("id") String videoId,
                                                         @Query("part") String part,
                                                         @Query("key") String key);
}
