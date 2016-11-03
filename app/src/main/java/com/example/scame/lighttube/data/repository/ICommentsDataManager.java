package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.List;

import rx.Observable;

public interface ICommentsDataManager {

    Observable<List<ThreadCommentModel>> getCommentList(String videoId, String order);

    Observable<ThreadCommentModel> postThreadComment(String commentText, String videoId);

    Observable<ThreadCommentModel> updateThreadComment(String updatedText, String commentId);

    Observable<List<ReplyModel>> getReplyList(String parentId);

    Observable<ReplyModel> postReply(String replyText, String parentId);

    Observable<String> deleteComment(String commentId);

    Observable<ReplyModel> updateReply(String replyText, String replyId);

    Observable<String> markAsSpam(String commentId);
}
