package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.mappers.CommentListMapper;
import com.example.scame.lighttube.data.mappers.ReplyListMapper;
import com.example.scame.lighttube.data.rest.CommentsApi;
import com.example.scame.lighttube.presentation.model.CommentListModel;
import com.example.scame.lighttube.presentation.model.ReplyListModel;

import rx.Observable;

public class CommentsDataManagerImp implements ICommentsDataManager {

    private static final String THREADS_PART = "snippet%2Creplies";

    private static final String REPLIES_PART = "snippet";

    private static final int MAX_RES = 50;

    private static final String TEXT_FORMAT = "plainText";

    private CommentsApi commentsApi;

    private CommentListMapper commentListMapper;

    private ReplyListMapper replyListMapper;

    public CommentsDataManagerImp(CommentsApi commentsApi, CommentListMapper commentListMapper,
                                  ReplyListMapper replyListMapper) {
        this.commentListMapper = commentListMapper;
        this.replyListMapper = replyListMapper;
        this.commentsApi = commentsApi;
    }

    @Override
    public Observable<CommentListModel> getCommentList(String videoId) {
        return commentsApi.getCommentThreads(THREADS_PART, MAX_RES, null, TEXT_FORMAT, videoId, PrivateValues.API_KEY)
                .map(commentListMapper::convert);
    }

    @Override
    public Observable<ReplyListModel> getReplyList(String parentId) {
        return commentsApi.getReplies(REPLIES_PART, MAX_RES, TEXT_FORMAT, null, parentId, PrivateValues.API_KEY)
                .map(replyListMapper::convert);
    }
}
