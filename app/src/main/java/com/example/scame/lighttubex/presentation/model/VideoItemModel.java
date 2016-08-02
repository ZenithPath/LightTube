package com.example.scame.lighttubex.presentation.model;

public class VideoItemModel {

    private String thumbnails;

    private String title;

    private String id;

    public VideoItemModel() { }

    public VideoItemModel(String thumbnails, String title, String id) {
        this.thumbnails = thumbnails;
        this.title = title;
        this.id = id;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
}
