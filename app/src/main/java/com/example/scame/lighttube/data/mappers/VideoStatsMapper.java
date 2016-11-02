package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.statistics.VideoStatisticsEntity;
import com.example.scame.lighttube.data.entities.statistics.VideoStatisticsItem;
import com.example.scame.lighttube.presentation.model.VideoStatsModel;

public class VideoStatsMapper {

    private static final int STATS_INDEX = 0;

    public VideoStatsModel convert(VideoStatisticsEntity statisticsEntity) {
        VideoStatsModel statsModel = new VideoStatsModel();

        VideoStatisticsItem statisticsItem = statisticsEntity.getItems().get(STATS_INDEX).getStatistics();
        statsModel.setCommentCount(Integer.valueOf(statisticsItem.getCommentCount()));
        statsModel.setDislikeCount(Integer.valueOf(statisticsItem.getDislikeCount()));
        statsModel.setLikeCount(Integer.valueOf(statisticsItem.getLikeCount()));
        statsModel.setViewCount(Integer.valueOf(statisticsItem.getViewCount()));
        statsModel.setVideoId(statisticsEntity.getItems().get(STATS_INDEX).getId());

        return statsModel;
    }
}
