package com.example.scame.lighttubex.data.rest;

import com.example.scame.lighttubex.data.entities.search.SearchEntity;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SearchApi {

    @GET("youtube/v3/search")
    Observable<SearchEntity> searchVideo(@Query("part") String part,
                                         @Query("q") String q,
                                         @Query("key") String key); // replace with interceptor

    @GET("http://suggestqueries.google.com/complete/search")
    Observable<ResponseBody> autocomplete(@Query("q") String q,
                                    @Query("client") String client,
                                    @Query("ds") String restrictTo,
                                    @Query("hl") String lang);
}
