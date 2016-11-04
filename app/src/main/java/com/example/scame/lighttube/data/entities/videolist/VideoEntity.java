package com.example.scame.lighttube.data.entities.videolist;


public class VideoEntity {

    private String id;

    private SnippetEntity snippet;

    public void setId(String id) {
        this.id = id;
    }

    public void setSnippet(SnippetEntity snippet) {
        this.snippet = snippet;
    }

    public String getId() {
        return id;
    }

    public SnippetEntity getSnippet() {
        return snippet;
    }
}
