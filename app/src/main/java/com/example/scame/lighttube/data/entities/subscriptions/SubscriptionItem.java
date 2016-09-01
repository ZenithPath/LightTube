package com.example.scame.lighttube.data.entities.subscriptions;


import com.example.scame.lighttube.data.entities.videolist.SnippetEntity;

public class SubscriptionItem {

    private SnippetEntity snippet;

    public void setSnippet(SnippetEntity snippet) {
        this.snippet = snippet;
    }

    public SnippetEntity getSnippet() {
        return snippet;
    }
}
