package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

public interface ICommentInputPresenter<T> extends Presenter<T> {

    interface CommentInputView {

        void displayPostedComment(ThreadCommentModel threadComment);
    }

    void postComment(String commentText, String videoId);
}
