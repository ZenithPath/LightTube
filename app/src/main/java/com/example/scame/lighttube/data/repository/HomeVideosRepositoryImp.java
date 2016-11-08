package com.example.scame.lighttube.data.repository;


import android.util.Log;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.mappers.HomeVideosMapper;
import com.example.scame.lighttube.data.rest.VideoListApi;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import rx.Observable;

public class HomeVideosRepositoryImp implements HomeVideosRepository {

    private static final String CHART = "mostPopular";
    private static final String PART = "snippet";
    private static final int MAX_RESULTS = 10;

    private HomeVideosMapper homeVideosMapper;
    private VideoListApi videoListApi;

    private PaginationUtility paginationUtility;

    public HomeVideosRepositoryImp(HomeVideosMapper homeVideosMapper, VideoListApi videoListApi,
                                   PaginationUtility paginationUtility) {
        this.homeVideosMapper = homeVideosMapper;
        this.videoListApi = videoListApi;
        this.paginationUtility = paginationUtility;
    }

    @Override
    public Observable<VideoModelsWrapper> getVideoItemsList(int page) {
        return videoListApi.getVideoList(paginationUtility.getNextPageToken(page),
                CHART, PrivateValues.API_KEY, PART, MAX_RESULTS)
                .doOnNext(videoEntityList -> {
                    paginationUtility.saveNextPageToken(videoEntityList.getNextPageToken());
                    Log.i("onxSavingToken", videoEntityList.getNextPageToken());
                    paginationUtility.saveCurrentPage(page);
                }).map(videosEntity -> homeVideosMapper.convert(videosEntity, page));
    }
}
