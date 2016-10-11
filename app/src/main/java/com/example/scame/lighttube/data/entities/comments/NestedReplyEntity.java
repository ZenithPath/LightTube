package com.example.scame.lighttube.data.entities.comments;


import java.util.ArrayList;
import java.util.List;

public class NestedReplyEntity {

    private List<CommentSnippetHolder> comments;

    public NestedReplyEntity() {
        comments = new ArrayList<>();
    }

    public void setComments(List<CommentSnippetHolder> comments) {
        this.comments = comments;
    }

    public List<CommentSnippetHolder> getComments() {
        return comments;
    }
}
