package com.example.scame.lighttube.data.entities.search;

import com.example.scame.lighttube.data.entities.videolist.SnippetEntity;

public class SearchItem {

    private SearchId id;

    private SnippetEntity snippet;

    public void setId(SearchId id) {
        this.id = id;
    }

    public SearchId getId() {
        return id;
    }

    public void setSnippet(SnippetEntity snippet) {
        this.snippet = snippet;
    }

    public SnippetEntity getSnippet() {
        return snippet;
    }
}
