package com.example.scame.lighttube.presentation.presenters;


import android.util.Pair;

import com.example.scame.lighttube.presentation.model.CommentListModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

public interface IPlayerFooterPresenter<T> extends Presenter<T> {

    interface FooterView {

        void displayComments(CommentListModel commentListModel, String userIdentifier);

        void onCommentDeleted(Pair<Integer, Integer> commentIndex);

        void onMarkedAsSpam(Pair<Integer, Integer> commentIndex);

        void onCommentUpdated(Pair<Integer, Integer> commentIndex, ThreadCommentModel threadCommentModel);

        void displayPostedComment(ThreadCommentModel threadComment);
    }

    void getCommentList(String videoId);

    void deleteThreadComment(String commentId, Pair<Integer, Integer> commentIndex);

    void markAsSpam(String commentId, Pair<Integer, Integer> commentIndex);

    void updateComment(String commentId, Pair<Integer, Integer> commentIndex, String updatedText);

    void postComment(String commentText, String videoId);
}
