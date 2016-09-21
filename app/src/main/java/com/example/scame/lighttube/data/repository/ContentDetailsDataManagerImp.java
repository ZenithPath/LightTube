package com.example.scame.lighttube.data.repository;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.entities.content.ContentEntity;
import com.example.scame.lighttube.data.mappers.IdsMapper;
import com.example.scame.lighttube.data.rest.VideoListApi;
import com.example.scame.lighttube.presentation.LightTubeApp;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

public class ContentDetailsDataManagerImp implements IContentDetailsDataManager {

    private static final String part = "contentDetails";

    private VideoListApi videoListApi;

    public ContentDetailsDataManagerImp() {
        Retrofit retrofit = LightTubeApp.getAppComponent().getRetrofit();
        videoListApi = retrofit.create(VideoListApi.class);
    }

    @Override
    public Observable<ContentEntity> getContentDetails(List<VideoModel> videoModels) {
        IdsMapper idsMapper = new IdsMapper();

        return videoListApi.getContentEntity(idsMapper.convert(videoModels), part, PrivateValues.API_KEY);
    }
}
