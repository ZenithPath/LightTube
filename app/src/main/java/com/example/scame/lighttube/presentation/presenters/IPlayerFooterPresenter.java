package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.CommentListModel;

public interface IPlayerFooterPresenter<T> extends Presenter<T> {

    interface CommentsView {

        void displayComments(CommentListModel commentListModel);
    }

    void getCommentList(String videoId);
}
