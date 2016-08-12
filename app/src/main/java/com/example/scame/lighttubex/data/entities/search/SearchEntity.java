package com.example.scame.lighttubex.data.entities.search;


import java.util.ArrayList;
import java.util.List;

public class SearchEntity {

    private List<SearchItem> items;

    private String nextPageToken;

    public SearchEntity() {
        items = new ArrayList<>();
    }

    public void setItems(List<SearchItem> items) {
        this.items = items;
    }

    public List<SearchItem> getItems() {
        return items;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }
}
