package com.example.scame.lighttube.data.rest;


import com.example.scame.lighttube.data.entities.comments.CommentThreadsEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface CommentsApi {

    @GET("youtube/v3/commentThreads")
    Observable<CommentThreadsEntity> getCommentThreads(@Query(encoded = true, value = "part") String part,
                                                       @Query("maxResults") Integer maxResults,
                                                       @Query("pageToken") String pageToken,
                                                       @Query("textFormat") String textFormat,
                                                       @Query("videoId") String videoId,
                                                       @Query("key") String key);
}
