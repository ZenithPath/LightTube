package com.example.scame.lighttube.data.entities.channels;


import com.example.scame.lighttube.data.entities.videolist.ThumbnailsGroup;

public class ChannelSnippet {

    private String title;

    private String description;

    private String publishedAt;

    private ThumbnailsGroup thumbnails;

    private ChannelLocalized localized;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setThumbnails(ThumbnailsGroup thumbnails) {
        this.thumbnails = thumbnails;
    }

    public void setLocalized(ChannelLocalized localized) {
        this.localized = localized;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public ThumbnailsGroup getThumbnails() {
        return thumbnails;
    }

    public ChannelLocalized getLocalized() {
        return localized;
    }
}
