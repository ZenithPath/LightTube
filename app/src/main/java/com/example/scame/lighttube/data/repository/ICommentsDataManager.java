package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.CommentListModel;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import rx.Observable;

public interface ICommentsDataManager {

    Observable<CommentListModel> getCommentList(String videoId);

    Observable<ThreadCommentModel> postThreadComment(String commentText, String videoId);

    Observable<ThreadCommentModel> updateThreadComment(String updatedText, String commentId);

    Observable<ReplyListModel> getReplyList(String parentId);

    Observable<ReplyModel> postReply(String replyText, String parentId);

    Observable<Void> deleteComment(String commentId);

    Observable<ReplyModel> updateReply(String replyText, String replyId);

    Observable<Void> markAsSpam(String commentId);
}
