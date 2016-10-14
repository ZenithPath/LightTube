package com.example.scame.lighttube.data.entities.channels;



public class ChannelItem {

    private String id;

    private ChannelSnippet snippet;

    public void setId(String id) {
        this.id = id;
    }

    public void setSnippet(ChannelSnippet snippet) {
        this.snippet = snippet;
    }

    public String getId() {
        return id;
    }

    public ChannelSnippet getSnippet() {
        return snippet;
    }
}
