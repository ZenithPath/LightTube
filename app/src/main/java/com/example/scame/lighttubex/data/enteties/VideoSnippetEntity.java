package com.example.scame.lighttubex.data.enteties;


public class VideoSnippetEntity {

    private String publishedAt;

    private String channelId;

    private String title;

    private String description;

    private ThumbnailsEntity thumbnails;

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnails(ThumbnailsEntity thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ThumbnailsEntity getThumbnails() {
        return thumbnails;
    }
}
