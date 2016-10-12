package com.example.scame.lighttube.data.entities.comments.requests;


public class ThreadCommentBody {

    private String id;

    private ThreadRequestSnippet snippet;

    public void setId(String id) {
        this.id = id;
    }

    public void setSnippet(ThreadRequestSnippet snippet) {
        this.snippet = snippet;
    }

    public String getId() {
        return id;
    }

    public ThreadRequestSnippet getSnippet() {
        return snippet;
    }
}
