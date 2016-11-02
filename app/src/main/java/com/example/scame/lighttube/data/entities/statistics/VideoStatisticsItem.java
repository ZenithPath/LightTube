package com.example.scame.lighttube.data.entities.statistics;



public class VideoStatisticsItem {

    private String viewCount;

    private String likeCount;

    private String dislikeCount;

    private String favoriteCount;

    private String commentCount;

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public void setDislikeCount(String dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getViewCount() {
        return viewCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public String getDislikeCount() {
        return dislikeCount;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public String getCommentCount() {
        return commentCount;
    }
}
