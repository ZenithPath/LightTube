package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.CommentListModel;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import rx.Observable;

public interface ICommentsDataManager {

    Observable<CommentListModel> getCommentList(String videoId);

    Observable<ThreadCommentModel> postThreadComment(String text, String videoId);

    Observable<ReplyListModel> getReplyList(String parentId);
}
