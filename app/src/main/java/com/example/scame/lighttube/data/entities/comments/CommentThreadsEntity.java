package com.example.scame.lighttube.data.entities.comments;


import java.util.ArrayList;
import java.util.List;

public class CommentThreadsEntity {

    private String nextPageToken;

    private List<CommentItem> items;

    public CommentThreadsEntity() {
        items = new ArrayList<>();
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public void setItems(List<CommentItem> items) {
        this.items = items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public List<CommentItem> getItems() {
        return items;
    }
}
