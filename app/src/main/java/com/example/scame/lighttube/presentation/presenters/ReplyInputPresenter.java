package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.ReplyModel;

public interface ReplyInputPresenter<T> extends Presenter<T> {

    interface ReplyView {

        void displayReply(ReplyModel replyModel);
    }

    void postReply(String parentId, String replyText);
}
