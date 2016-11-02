package com.example.scame.lighttube.data.entities.statistics;


import java.util.ArrayList;
import java.util.List;

public class VideoStatisticsEntity {

    private List<VideoStatisticsHolder> items;

    public VideoStatisticsEntity() {
        items = new ArrayList<>();
    }

    public void setItems(List<VideoStatisticsHolder> items) {
        this.items = items;
    }

    public List<VideoStatisticsHolder> getItems() {
        return items;
    }
}
