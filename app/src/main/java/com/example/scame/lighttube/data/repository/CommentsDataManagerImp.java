package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.mappers.CommentListMapper;
import com.example.scame.lighttube.data.mappers.ReplyListMapper;
import com.example.scame.lighttube.data.mappers.ReplyResponseMapper;
import com.example.scame.lighttube.data.mappers.ReplyPostBuilder;
import com.example.scame.lighttube.data.mappers.ReplyUpdateBuilder;
import com.example.scame.lighttube.data.mappers.ThreadPostBuilder;
import com.example.scame.lighttube.data.mappers.ThreadResponseMapper;
import com.example.scame.lighttube.data.mappers.ThreadUpdateBuilder;
import com.example.scame.lighttube.data.rest.CommentsApi;
import com.example.scame.lighttube.presentation.model.CommentListModel;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import rx.Observable;

public class CommentsDataManagerImp implements ICommentsDataManager {

    private static final String SNIPPET_AND_REPLIES_PART = "snippet%2Creplies";

    private static final String SNIPPET_PART = "snippet";

    private static final String THREADS_ORDER = "relevance";

    private static final int MAX_RES = 50;

    private static final String TEXT_FORMAT = "plainText";

    private CommentsApi commentsApi;

    private CommentListMapper commentListMapper;

    private ReplyListMapper replyListMapper;

    private ThreadResponseMapper threadResponseMapper;

    private ThreadPostBuilder threadPostBuilder;

    private ReplyPostBuilder replyPostBuilder;

    private ReplyResponseMapper replyResponseMapper;

    private ReplyUpdateBuilder replyUpdateBuilder;

    private ThreadUpdateBuilder threadUpdateBuilder;

    public CommentsDataManagerImp(CommentsApi commentsApi, CommentListMapper commentListMapper,
                                  ReplyListMapper replyListMapper, ThreadResponseMapper threadResponseMapper,
                                  ThreadPostBuilder threadPostBuilder, ReplyPostBuilder replyPostBuilder,
                                  ReplyResponseMapper replyResponseMapper, ReplyUpdateBuilder replyUpdateBuilder,
                                  ThreadUpdateBuilder threadUpdateBuilder) {
        this.commentListMapper = commentListMapper;
        this.replyListMapper = replyListMapper;
        this.commentsApi = commentsApi;
        this.threadResponseMapper = threadResponseMapper;
        this.threadPostBuilder = threadPostBuilder;
        this.replyPostBuilder = replyPostBuilder;
        this.replyResponseMapper = replyResponseMapper;
        this.replyUpdateBuilder = replyUpdateBuilder;
        this.threadUpdateBuilder = threadUpdateBuilder;
    }

    @Override
    public Observable<CommentListModel> getCommentList(String videoId) {
        return commentsApi.getCommentThreads(SNIPPET_AND_REPLIES_PART, THREADS_ORDER,MAX_RES, null,
                TEXT_FORMAT, videoId, PrivateValues.API_KEY)
                .map(commentListMapper::convert);
    }

    @Override
    public Observable<ReplyListModel> getReplyList(String parentId) {
        return commentsApi.getReplies(SNIPPET_PART, MAX_RES, TEXT_FORMAT, null, parentId, PrivateValues.API_KEY)
                .map(replyListMapper::convert);
    }

    @Override
    public Observable<ThreadCommentModel> postThreadComment(String text, String videoId) {
        return commentsApi.postThreadComment(SNIPPET_PART, PrivateValues.API_KEY, threadPostBuilder.build(text, videoId))
                .map(threadResponseMapper::convert);
    }

    @Override
    public Observable<ReplyModel> postReply(String replyText, String parentId) {
        return commentsApi.postReply(SNIPPET_PART, PrivateValues.API_KEY, replyPostBuilder.build(parentId, replyText))
                .map(replyResponseMapper::convert);
    }

    @Override
    public Observable<Void> deleteComment(String commentId) {
        return commentsApi.deleteComment(commentId, PrivateValues.API_KEY);
    }

    @Override
    public Observable<ReplyModel> updateReply(String replyText, String replyId) {
        return commentsApi.updateReply(SNIPPET_PART, PrivateValues.API_KEY,
                replyUpdateBuilder.build(replyText, replyId))
                .map(replyResponseMapper::convert);
    }

    @Override
    public Observable<ThreadCommentModel> updateThreadComment(String updatedText, String commentId) {
        return commentsApi.updateThreadComment(SNIPPET_PART, PrivateValues.API_KEY,
                threadUpdateBuilder.build(updatedText, commentId))
                .map(threadResponseMapper::convert);
    }

    @Override
    public Observable<Void> markAsSpam(String commentId) {
        return commentsApi.markAsSpam(commentId, PrivateValues.API_KEY);
    }
}
