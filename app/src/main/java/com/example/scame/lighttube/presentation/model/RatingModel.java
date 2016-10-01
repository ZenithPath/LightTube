package com.example.scame.lighttube.presentation.model;


public class RatingModel {

    private String videoId;

    private String rating;

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getRating() {
        return rating;
    }
}
