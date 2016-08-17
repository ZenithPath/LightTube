package com.example.scame.lighttube.data.entities.videolist;


import java.util.ArrayList;
import java.util.List;

public class VideoEntityList {

    private List<VideoEntity> items;

    private String nextPageToken;

    private String prevPageToken;

    public VideoEntityList() {
        items = new ArrayList<>();
    }

    public void setItems(List<VideoEntity> items) {
        this.items = items;
    }


    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public void setPrevPageToken(String prevPageToken) {
        this.prevPageToken = prevPageToken;
    }

    public List<VideoEntity> getItems() {
        return items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public String getPrevPageToken() {
        return prevPageToken;
    }
}
