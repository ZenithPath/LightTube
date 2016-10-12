package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.mappers.CommentListMapper;
import com.example.scame.lighttube.data.mappers.ReplyListMapper;
import com.example.scame.lighttube.data.mappers.ThreadRequestBuilder;
import com.example.scame.lighttube.data.mappers.ThreadRequestMapper;
import com.example.scame.lighttube.data.rest.CommentsApi;
import com.example.scame.lighttube.presentation.model.CommentListModel;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import rx.Observable;

public class CommentsDataManagerImp implements ICommentsDataManager {

    private static final String THREAD_PART_GET = "snippet%2Creplies";

    private static final String REPLIES_PART = "snippet";

    private static final String THREAD_PART_POST = "snippet";

    private static final int MAX_RES = 50;

    private static final String TEXT_FORMAT = "plainText";

    private CommentsApi commentsApi;

    private CommentListMapper commentListMapper;

    private ReplyListMapper replyListMapper;

    private ThreadRequestMapper requestMapper;

    private ThreadRequestBuilder commentBuilder;

    public CommentsDataManagerImp(CommentsApi commentsApi, CommentListMapper commentListMapper,
                                  ReplyListMapper replyListMapper, ThreadRequestMapper requestMapper,
                                  ThreadRequestBuilder commentBuilder) {
        this.commentListMapper = commentListMapper;
        this.replyListMapper = replyListMapper;
        this.commentsApi = commentsApi;
        this.requestMapper = requestMapper;
        this.commentBuilder = commentBuilder;
    }

    @Override
    public Observable<CommentListModel> getCommentList(String videoId) {
        return commentsApi.getCommentThreads(THREAD_PART_GET, MAX_RES, null, TEXT_FORMAT, videoId, PrivateValues.API_KEY)
                .map(commentListMapper::convert);
    }

    @Override
    public Observable<ReplyListModel> getReplyList(String parentId) {
        return commentsApi.getReplies(REPLIES_PART, MAX_RES, TEXT_FORMAT, null, parentId, PrivateValues.API_KEY)
                .map(replyListMapper::convert);
    }

    @Override
    public Observable<ThreadCommentModel> postThreadComment(String text, String videoId) {
        return commentsApi.postThreadComment(THREAD_PART_POST, PrivateValues.API_KEY, commentBuilder.build(text, videoId))
                .map(requestMapper::convert);
    }
}
