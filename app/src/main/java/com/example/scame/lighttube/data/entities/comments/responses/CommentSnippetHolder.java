package com.example.scame.lighttube.data.entities.comments.responses;


public class CommentSnippetHolder {

    private String id;

    private CommentSnippet snippet;

    public void setId(String id) {
        this.id = id;
    }

    public void setSnippet(CommentSnippet snippet) {
        this.snippet = snippet;
    }

    public String getId() {
        return id;
    }

    public CommentSnippet getSnippet() {
        return snippet;
    }
}
