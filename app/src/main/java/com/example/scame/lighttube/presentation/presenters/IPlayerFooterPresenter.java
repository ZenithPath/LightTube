package com.example.scame.lighttube.presentation.presenters;


import android.util.Pair;

import com.example.scame.lighttube.presentation.model.CommentListModel;

public interface IPlayerFooterPresenter<T> extends Presenter<T> {

    interface FooterView {

        void displayComments(CommentListModel commentListModel, String userIdentifier);

        void onCommentDeleted(Pair<Integer, Integer> commentIndex);
    }

    void getCommentList(String videoId);

    void deleteThreadComment(String commentId, Pair<Integer, Integer> commentIndex);
}
