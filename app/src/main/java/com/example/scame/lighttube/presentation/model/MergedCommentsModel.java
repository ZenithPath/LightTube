package com.example.scame.lighttube.presentation.model;


import java.util.List;

public class MergedCommentsModel {

    private List<ThreadCommentModel> threadCommentModels;

    private VideoStatsModel videoStatsModel;

    private String userIdentifier;

    public MergedCommentsModel() {}

    public MergedCommentsModel(List<ThreadCommentModel> threadCommentModels,
                               VideoStatsModel videoStatsModel, String userIdentifier) {
        this.threadCommentModels = threadCommentModels;
        this.videoStatsModel = videoStatsModel;
        this.userIdentifier = userIdentifier;
    }

    public void setThreadCommentModels(List<ThreadCommentModel> threadCommentModels) {
        this.threadCommentModels = threadCommentModels;
    }

    public void setVideoStatsModel(VideoStatsModel videoStatsModel) {
        this.videoStatsModel = videoStatsModel;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public List<ThreadCommentModel> getThreadCommentModels() {
        return threadCommentModels;
    }

    public VideoStatsModel getVideoStatsModel() {
        return videoStatsModel;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }
}
