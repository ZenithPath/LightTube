package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;
import com.example.scame.lighttube.presentation.model.VideoStatsModel;

import java.util.List;

public interface IPlayerFooterPresenter<T> extends Presenter<T> {

    interface FooterView {

        void onInitialized(List<?> comments, String userIdentifier, String commentsOrder, VideoStatsModel statsModel);

        void onCommentsUpdated(List<?> comments, String commentsOrder);

        void onCommentDeleted(String deletedCommentId);

        void onMarkedAsSpam(String markedCommentId);

        void onCommentUpdated(ThreadCommentModel threadCommentModel);

        void onReplyUpdated(ReplyModel replyModel);

        void displayPostedComment(ThreadCommentModel threadComment);
    }

    void initializeFooter(String videoId, String order, int page);

    void commentsOrderClick(String videoId, String previousOrder, String newOrder, int page);

    void getCommentList(String videoId, String order, int page);

    void deleteThreadComment(String commentId);

    void markAsSpam(String commentId);

    void updateComment(String commentId, String updatedText);

    void postComment(String commentText, String videoId);

    void updateReply(String commentId, String updatedText);
}
