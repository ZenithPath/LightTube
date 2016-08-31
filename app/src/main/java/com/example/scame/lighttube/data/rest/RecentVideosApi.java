package com.example.scame.lighttube.data.rest;


import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionsEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RecentVideosApi {

    @GET("youtube/v3/subscriptions")
    Observable<SubscriptionsEntity> getSubscriptions(@Query("part") String part,
                                                     @Query("maxResults") int maxResults,
                                                     @Query("mine") boolean mine,
                                                     @Query("key") String key);
}
