package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

public interface IRepliesPresenter<T> extends Presenter<T> {

    interface RepliesView {

        void displayReplies(ReplyListModel replyListModel);

        void onDeletedComment(int position);

        void onMarkedAsSpam(int position);

        void onUpdatedReply(int position, ReplyModel replyModel);

        void onUpdatedPrimaryComment(ThreadCommentModel threadCommentModel);
    }

    void getRepliesList(String parentId);

    void deleteComment(String replyId, int position);

    void markAsSpam(String replyId, int position);

    void updateReply(String replyId, String updatedText, int position, String userIdentifier);

    void updatePrimaryComment(String commentId, String updatedText);
}
