package com.example.scame.lighttube.presentation.model;


public class ReplyModel {

    private String textDisplay;

    private String profileImageUrl;

    private String authorName;

    private String date;

    private String parentId;

    private String commentId;

    private String authorChannelId;

    public void setAuthorChannelId(String authorChannelId) {
        this.authorChannelId = authorChannelId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public void setTextDisplay(String textDisplay) {
        this.textDisplay = textDisplay;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getTextDisplay() {
        return textDisplay;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getDate() {
        return date;
    }

    public String getParentId() {
        return parentId;
    }

    public String getAuthorChannelId() {
        return authorChannelId;
    }
}
