package com.example.scame.lighttube.data.rest;


import com.example.scame.lighttube.data.entities.channels.ChannelEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ChannelsApi {

    @GET("youtube/v3/channels")
    Observable<ChannelEntity> getCurrentUserChannel(@Query("part") String part,
                                                    @Query("mine") Boolean mine,
                                                    @Query("key") String key);
}
