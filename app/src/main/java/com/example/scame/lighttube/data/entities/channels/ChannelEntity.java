package com.example.scame.lighttube.data.entities.channels;


import java.util.ArrayList;
import java.util.List;

public class ChannelEntity {

    private String nextPageToken;

    private List<ChannelItem> items;

    public ChannelEntity() {
        items = new ArrayList<>();
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public void setItems(List<ChannelItem> items) {
        this.items = items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public List<ChannelItem> getItems() {
        return items;
    }
}
