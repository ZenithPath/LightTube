package com.example.scame.lighttube.data.entities.comments;


public class CommentItem {

    private String id;

    private ThreadSnippet snippet;

    private ReplyEntity replies;

    public void setReplies(ReplyEntity replies) {
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

    public ReplyEntity getReplies() {
        return replies;
    }
}
