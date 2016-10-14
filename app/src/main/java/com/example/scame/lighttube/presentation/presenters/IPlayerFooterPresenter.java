package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.CommentListModel;

public interface IPlayerFooterPresenter<T> extends Presenter<T> {

    interface FooterView {

        void displayComments(CommentListModel commentListModel, String userIdentifier);
    }

    void getCommentList(String videoId);
}
