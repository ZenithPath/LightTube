package com.example.scame.lighttube.data.entities.search;

import com.example.scame.lighttube.data.entities.videolist.VideoSnippetEntity;

public class SearchItem {

    private SearchId id;

    private VideoSnippetEntity snippet;

    public void setId(SearchId id) {
        this.id = id;
    }

    public SearchId getId() {
        return id;
    }

    public void setSnippet(VideoSnippetEntity snippet) {
        this.snippet = snippet;
    }

    public VideoSnippetEntity getSnippet() {
        return snippet;
    }
}
