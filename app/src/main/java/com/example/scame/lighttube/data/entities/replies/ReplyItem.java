package com.example.scame.lighttube.data.entities.replies;

public class ReplyItem {

    private String id;

    private ReplySnippet snippet;

    public void setId(String id) {
        this.id = id;
    }

    public void setSnippet(ReplySnippet snippet) {
        this.snippet = snippet;
    }

    public String getId() {
        return id;
    }

    public ReplySnippet getSnippet() {
        return snippet;
    }
}
