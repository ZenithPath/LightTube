package com.example.scame.lighttube.data.rest;


import com.example.scame.lighttube.data.entities.content.ContentEntity;
import com.example.scame.lighttube.data.entities.videolist.VideoEntityList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface VideoListApi {

    @GET("youtube/v3/videos")
    Observable<VideoEntityList> getVideoList(@Query("pageToken") String pageToken,
                                             @Query("chart") String chart,
                                             @Query("key") String key,
                                             @Query("part") String part,
                                             @Query("maxResults") Integer maxResults);

    @GET("youtube/v3/videos")
    Observable<ContentEntity> getContentEntity(@Query(value = "id", encoded = true) String ids,
                                               @Query("part") String part,
                                               @Query("key") String key);
}
