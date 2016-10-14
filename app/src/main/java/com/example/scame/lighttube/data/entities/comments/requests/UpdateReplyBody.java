package com.example.scame.lighttube.data.entities.comments.requests;



public class UpdateReplyBody {

    private UpdateReplySnippet snippet;

    private String id;

    public void setSnippet(UpdateReplySnippet snippet) {
        this.snippet = snippet;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UpdateReplySnippet getSnippet() {
        return snippet;
    }

    public String getId() {
        return id;
    }
}
