package com.example.scame.lighttube.presentation.model;



public class VideoStatsModel {

    private String videoId;

    private int viewCount;

    private int likeCount;

    private int dislikeCount;

    private int commentCount;

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getVideoId() {
        return videoId;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }
}
