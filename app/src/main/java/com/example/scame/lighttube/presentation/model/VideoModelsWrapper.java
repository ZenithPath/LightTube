package com.example.scame.lighttube.presentation.model;


import java.util.List;

public class VideoModelsWrapper {

    private List<VideoModel> videoModels;

    private int page;

    public VideoModelsWrapper(List<VideoModel> videoModels, int page) {
        this.videoModels = videoModels;
        this.page = page;
    }

    public List<VideoModel> getVideoModels() {
        return videoModels;
    }

    public int getPage() {
        return page;
    }
}
