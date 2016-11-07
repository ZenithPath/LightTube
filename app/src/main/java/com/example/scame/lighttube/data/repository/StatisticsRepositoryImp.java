package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.mappers.VideoStatsMapper;
import com.example.scame.lighttube.data.rest.StatisticsApi;
import com.example.scame.lighttube.presentation.model.VideoStatsModel;

import rx.Observable;

public class StatisticsRepositoryImp implements StatisticsRepository {

    private static final String PART = "statistics";

    private VideoStatsMapper statsMapper;

    private StatisticsApi statisticsApi;

    public StatisticsRepositoryImp(VideoStatsMapper statsMapper, StatisticsApi statisticsApi) {
        this.statsMapper = statsMapper;
        this.statisticsApi = statisticsApi;
    }

    @Override
    public Observable<VideoStatsModel> getVideoStatistics(String videoId) {
        return statisticsApi.getVideoStatistics(videoId, PART, PrivateValues.API_KEY)
                .map(statsMapper::convert);
    }
}