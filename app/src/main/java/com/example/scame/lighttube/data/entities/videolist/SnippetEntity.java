package com.example.scame.lighttube.data.entities.videolist;


import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionResourceId;

public class SnippetEntity {

    private String publishedAt;

    private String channelId;

    private String channelTitle;

    private String description;

    private String title;

    private ThumbnailsGroup thumbnails;

    private SubscriptionResourceId resourceId;

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setResourceId(SubscriptionResourceId resourceId) {
        this.resourceId = resourceId;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnails(ThumbnailsGroup thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getDescription() {
        return description;
    }

    public ThumbnailsGroup getThumbnails() {
        return thumbnails;
    }

    public SubscriptionResourceId getResourceId() {
        return resourceId;
    }
}
