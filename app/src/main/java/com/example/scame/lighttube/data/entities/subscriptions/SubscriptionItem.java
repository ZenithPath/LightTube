package com.example.scame.lighttube.data.entities.subscriptions;


import com.example.scame.lighttube.data.entities.videolist.SnippetEntity;
import com.example.scame.lighttube.data.entities.videolist.ThumbnailsGroup;

public class SubscriptionItem {

    private SnippetEntity snippet;

    private SubscriptionResourceId resourceId;

    private ThumbnailsGroup thumbnails;

    public void setSnippet(SnippetEntity snippet) {
        this.snippet = snippet;
    }

    public void setResourceId(SubscriptionResourceId resourceId) {
        this.resourceId = resourceId;
    }

    public void setThumbnails(ThumbnailsGroup thumbnails) {
        this.thumbnails = thumbnails;
    }

    public SnippetEntity getSnippet() {
        return snippet;
    }

    public SubscriptionResourceId getResourceId() {
        return resourceId;
    }

    public ThumbnailsGroup getThumbnails() {
        return thumbnails;
    }
}
