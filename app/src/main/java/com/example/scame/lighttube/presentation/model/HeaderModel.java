package com.example.scame.lighttube.presentation.model;



public class HeaderModel {

    private String videoId;

    private String videoTitle;

    private String videoDescription;

    private String videoCategory;

    private String license;

    private int viewCount;

    private int dislikeCount;

    private int likeCount;

    public HeaderModel() { }

    // TODO: decode category & add license info
    public HeaderModel(VideoModel videoModel, VideoStatsModel statsModel) {
        this.videoId = videoModel.getVideoId();
        this.videoTitle = videoModel.getTitle();
        this.videoDescription = videoModel.getDescription();
        this.videoCategory = videoModel.getCategory();
        this.license = "no info available";
        this.viewCount = statsModel.getViewCount();
        this.dislikeCount = statsModel.getDislikeCount();
        this.likeCount = statsModel.getLikeCount();
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public void setVideoCategory(String videoCategory) {
        this.videoCategory = videoCategory;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public String getVideoCategory() {
        return videoCategory;
    }

    public String getLicense() {
        return license;
    }
}
