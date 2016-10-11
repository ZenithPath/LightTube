package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.ReplyListModel;

public interface IRepliesPresenter<T> extends Presenter<T> {

    interface RepliesView {

        void displayReplies(ReplyListModel replyListModel);
    }

    void getRepliesList(String parentId);
}
