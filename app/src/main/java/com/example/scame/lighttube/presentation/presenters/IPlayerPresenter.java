package com.example.scame.lighttube.presentation.presenters;

import com.example.scame.lighttube.presentation.model.CommentListModel;

public interface IPlayerPresenter<T> extends Presenter<T> {

    interface PlayerView {

        void displayRating(String rating);

        void displayComments(CommentListModel commentListModel);
    }

    void getVideoRating(String videoId);

    void rateVideo(String videoId, String rating);

    void getCommentList(String videoId);
}
