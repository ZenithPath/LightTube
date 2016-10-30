package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.List;

public interface IPlayerFooterPresenter<T> extends Presenter<T> {

    interface FooterView {

        void displayComments(List<?> models, String userIdentifier);

        void onCommentDeleted(String deletedCommentId);

        void onMarkedAsSpam(String markedCommentId);

        void onCommentUpdated(ThreadCommentModel threadCommentModel);

        void onReplyUpdated(ReplyModel replyModel);

        void displayPostedComment(ThreadCommentModel threadComment);
    }

    void getCommentList(String videoId);

    void deleteThreadComment(String commentId);

    void markAsSpam(String commentId);

    void updateComment(String commentId, String updatedText);

    void postComment(String commentText, String videoId);

    void updateReply(String commentId, String updatedText);
}
