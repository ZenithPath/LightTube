package com.example.scame.lighttube.data.entities.comments.responses;


public class CommentSnippet {

    private String authorDisplayName;

    private String authorProfileImageUrl;

    private String authorChannelUrl;

    private String videoId;

    private String textDisplay; // used in GET requests

    private boolean canRate;

    private String viewerRating;

    private Integer likeCount;

    private String publishedAt;

    private String updatedAt;

    private String parentId;

    private String textOriginal; // used in POST requests

    public void setTextOriginal(String textOriginal) {
        this.textOriginal = textOriginal;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setAuthorDisplayName(String authorDisplayName) {
        this.authorDisplayName = authorDisplayName;
    }

    public void setAuthorProfileImageUrl(String authorProfileImageUrl) {
        this.authorProfileImageUrl = authorProfileImageUrl;
    }

    public void setAuthorChannelUrl(String authorChannelUrl) {
        this.authorChannelUrl = authorChannelUrl;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setTextDisplay(String textDisplay) {
        this.textDisplay = textDisplay;
    }

    public void setCanRate(boolean canRate) {
        this.canRate = canRate;
    }

    public void setViewerRating(String viewerRating) {
        this.viewerRating = viewerRating;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getParentId() {
        return parentId;
    }

    public String getTextOriginal() {
        return textOriginal;
    }

    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    public String getAuthorProfileImageUrl() {
        return authorProfileImageUrl;
    }

    public String getAuthorChannelUrl() {
        return authorChannelUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTextDisplay() {
        return textDisplay;
    }

    public boolean isCanRate() {
        return canRate;
    }

    public String getViewerRating() {
        return viewerRating;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
