package com.example.scame.lighttube.data.entities.comments.requests;


public class ReplyRequestBody {

    private ReplyRequestSnippet snippet;

    public void setSnippet(ReplyRequestSnippet snippet) {
        this.snippet = snippet;
    }

    public ReplyRequestSnippet getSnippet() {
        return snippet;
    }
}
