package com.example.scame.lighttube.data.repository;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.mappers.DurationsCombiner;
import com.example.scame.lighttube.data.mappers.IdsMapper;
import com.example.scame.lighttube.data.rest.VideoListApi;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public class ContentDetailsDataManagerImp implements IContentDetailsDataManager {

    private static final String part = "contentDetails";

    private VideoListApi videoListApi;

    private IdsMapper idsMapper;

    private DurationsCombiner combiner;

    public ContentDetailsDataManagerImp(VideoListApi videoListApi, IdsMapper idsMapper, DurationsCombiner durationsCombiner) {
        this.videoListApi = videoListApi;
        this.idsMapper = idsMapper;
        this.combiner = durationsCombiner;
    }

    @Override
    public Observable<List<VideoModel>> getContentDetails(List<VideoModel> videoModels) {
        return videoListApi.getContentEntity(idsMapper.convert(videoModels), part, PrivateValues.API_KEY)
                .map(contentEntity -> combiner.combine(contentEntity, videoModels)); // combine old video models and durations
    }
}
