package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.mappers.HomeVideosMapper;
import com.example.scame.lighttube.data.rest.VideoListApi;
import com.example.scame.lighttube.presentation.LightTubeApp;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import javax.inject.Inject;

import rx.Observable;

public class HomeVideosRepositoryImp implements HomeVideosRepository {

    private static final String CHART = "mostPopular";
    private static final String PART = "snippet";
    private static final int MAX_RESULTS = 10;

    private HomeVideosMapper homeVideosMapper;
    private VideoListApi videoListApi;

    @Inject
    PaginationUtility paginationUtility;

    public HomeVideosRepositoryImp(HomeVideosMapper homeVideosMapper, VideoListApi videoListApi) {
        this.homeVideosMapper = homeVideosMapper;
        this.videoListApi = videoListApi;

        LightTubeApp.getAppComponent().inject(this);
        paginationUtility.setTokenStringId(R.string.next_page_general);
        paginationUtility.setPageStringId(R.string.page_number_general);
    }

    @Override
    public Observable<VideoModelsWrapper> getVideoItemsList(int page) {
        return videoListApi.getVideoList(paginationUtility.getNextPageToken(page),
                CHART, PrivateValues.API_KEY, PART, MAX_RESULTS)
                .doOnNext(videoEntityList -> {
                    paginationUtility.saveNextPageToken(videoEntityList.getNextPageToken());
                    paginationUtility.saveCurrentPage(page);
                }).map(videosEntity -> homeVideosMapper.convert(videosEntity, page));
    }
}
