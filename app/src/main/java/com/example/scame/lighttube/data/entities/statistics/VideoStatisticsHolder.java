package com.example.scame.lighttube.data.entities.statistics;


public class VideoStatisticsHolder {

    private String id;

    private VideoStatisticsItem statistics;

    public void setId(String id) {
        this.id = id;
    }

    public void setStatistics(VideoStatisticsItem statistics) {
        this.statistics = statistics;
    }

    public String getId() {
        return id;
    }

    public VideoStatisticsItem getStatistics() {
        return statistics;
    }
}
