package com.example.scame.lighttube.presentation.model;


public class ChannelModel {

    private String channelId;

    private String imageUrl;

    private String title;

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }
}
