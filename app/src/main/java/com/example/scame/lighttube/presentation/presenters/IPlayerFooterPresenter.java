package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.CommentListModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

public interface IPlayerFooterPresenter<T> extends Presenter<T> {

    interface CommentsView {

        void displayComments(CommentListModel commentListModel);

        void displayPostedComment(ThreadCommentModel threadComment);
    }

    void getCommentList(String videoId);

    void postComment(String commentText, String videoId);
}
