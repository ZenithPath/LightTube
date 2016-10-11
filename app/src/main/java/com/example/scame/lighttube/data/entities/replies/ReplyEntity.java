package com.example.scame.lighttube.data.entities.replies;


import java.util.ArrayList;
import java.util.List;

public class ReplyEntity {

    private String nextPageToken;

    private List<ReplyItem> items;

    public ReplyEntity() {
        items = new ArrayList<>();
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public void setItems(List<ReplyItem> items) {
        this.items = items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public List<ReplyItem> getItems() {
        return items;
    }
}
