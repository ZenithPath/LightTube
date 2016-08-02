package com.example.scame.lighttubex.data.entities;


public class VideoEntity {

    private String id;

    private String channelTitle;

    private VideoSnippetEntity snippet;

    public void setId(String id) {
        this.id = id;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public void setSnippet(VideoSnippetEntity snippet) {
        this.snippet = snippet;
    }

    public String getId() {
        return id;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public VideoSnippetEntity getSnippet() {
        return snippet;
    }
}
