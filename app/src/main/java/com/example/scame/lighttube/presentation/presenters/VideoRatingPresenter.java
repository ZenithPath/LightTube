package com.example.scame.lighttube.presentation.presenters;

public interface VideoRatingPresenter<T> extends Presenter<T> {

    interface PlayerView {

        void displayRating(String rating);
    }

    void getVideoRating(String videoId);

    void rateVideo(String videoId, String rating);
}
