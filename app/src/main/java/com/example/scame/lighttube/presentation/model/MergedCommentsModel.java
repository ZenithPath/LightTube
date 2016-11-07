package com.example.scame.lighttube.presentation.model;


public class MergedCommentsModel {

    private ThreadCommentsWrapper commentsWrapper;

    private VideoStatsModel videoStatsModel;

    private String userIdentifier;

    public MergedCommentsModel(ThreadCommentsWrapper commentsWrapper,
                               VideoStatsModel videoStatsModel, String userIdentifier) {
        this.commentsWrapper = commentsWrapper;
        this.videoStatsModel = videoStatsModel;
        this.userIdentifier = userIdentifier;
    }

    public ThreadCommentsWrapper getCommentsWrapper() {
        return commentsWrapper;
    }

    public VideoStatsModel getVideoStatsModel() {
        return videoStatsModel;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }
}
