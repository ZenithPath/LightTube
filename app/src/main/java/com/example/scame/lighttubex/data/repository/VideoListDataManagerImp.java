package com.example.scame.lighttubex.data.repository;


import com.example.scame.lighttubex.PrivateValues;
import com.example.scame.lighttubex.data.mappers.VideoListMapper;
import com.example.scame.lighttubex.data.rest.VideoListApi;
import com.example.scame.lighttubex.presentation.LightTubeApp;
import com.example.scame.lighttubex.presentation.model.VideoItemModel;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

public class VideoListDataManagerImp implements IVideoListDataManager {

    private Retrofit retrofit;
    private VideoListMapper mapper;

    public VideoListDataManagerImp() {
        retrofit = LightTubeApp.getAppComponent().getRetrofit();
        mapper = new VideoListMapper();
    }

    @Override
    public Observable<List<VideoItemModel>> getVideoItemsList() {
        // temporary solution
        VideoListApi videoListApi = retrofit.create(VideoListApi.class);
        return videoListApi
                .getVideoList(null, "mostPopular", PrivateValues.API_KEY, "snippet", 25)
                .map(videoEntityList -> mapper.convert(videoEntityList));
    }
}
