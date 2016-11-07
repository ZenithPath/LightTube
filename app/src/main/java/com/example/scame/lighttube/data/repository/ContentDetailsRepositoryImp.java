package com.example.scame.lighttube.data.repository;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.mappers.DurationsCombiner;
import com.example.scame.lighttube.data.mappers.IdsMapper;
import com.example.scame.lighttube.data.rest.VideoListApi;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import rx.Observable;

public class ContentDetailsRepositoryImp implements ContentDetailsRepository {

    private static final String part = "contentDetails";

    private VideoListApi videoListApi;

    private IdsMapper idsMapper;

    private DurationsCombiner combiner;

    public ContentDetailsRepositoryImp(VideoListApi videoListApi, IdsMapper idsMapper, DurationsCombiner durationsCombiner) {
        this.videoListApi = videoListApi;
        this.idsMapper = idsMapper;
        this.combiner = durationsCombiner;
    }

    @Override
    public Observable<VideoModelsWrapper> getContentDetails(VideoModelsWrapper modelsWrapper) {
        return videoListApi.getContentEntity(idsMapper.convert(modelsWrapper.getVideoModels()), part, PrivateValues.API_KEY)
                .map(contentEntity -> combiner.combine(contentEntity, modelsWrapper));
    }
}
