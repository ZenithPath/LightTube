package com.example.scame.lighttube.data.entities.comments.responses;


public class CommentItem {

    private String id;

    private ThreadSnippet snippet;

    private NestedReplyEntity replies;

    public void setReplies(NestedReplyEntity replies) {
        this.replies = replies;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSnippet(ThreadSnippet snippet) {
        this.snippet = snippet;    }

    public ThreadSnippet getSnippet() {
        return snippet;
    }

    public String getId() {
        return id;
    }

    public NestedReplyEntity getReplies() {
        return replies;
    }
}
