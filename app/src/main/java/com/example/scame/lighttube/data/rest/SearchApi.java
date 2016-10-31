package com.example.scame.lighttube.data.rest;

import com.example.scame.lighttube.data.entities.search.CategoriesEntity;
import com.example.scame.lighttube.data.entities.search.SearchEntity;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SearchApi {

    @GET("youtube/v3/search")
    Observable<SearchEntity> searchVideo(@Query("part") String part,
                                         @Query("type") String type,
                                         @Query("q") String q,
                                         @Query("maxResults") Integer maxResults,
                                         @Query("pageToken") String pageToken,
                                         @Query("key") String key); // replace with interceptor


    @GET("http://suggestqueries.google.com/complete/search")
    Observable<ResponseBody> autocomplete(@Query("q") String q,
                                          @Query("client") String client,
                                          @Query("ds") String restrictTo,
                                          @Query("hl") String lang);

    @GET("youtube/v3/search")
    Observable<SearchEntity> searchVideoWithCategory(@Query("part") String part,
                                                     @Query("videoCategoryId") String categoryId,
                                                     @Query("videoDuration") String videoDuration,
                                                     @Query("type") String type,
                                                     @Query("maxResults") Integer maxResults,
                                                     @Query("pageToken") String pageToken,
                                                     @Query("key") String key);

    @GET("youtube/v3/videoCategories")
    Observable<CategoriesEntity> getCategoriesId(@Query("part") String part,
                                                 @Query("regionCode") String regionCode,
                                                 @Query("key") String key);
}
