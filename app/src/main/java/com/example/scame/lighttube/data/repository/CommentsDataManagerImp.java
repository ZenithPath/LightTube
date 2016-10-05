package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.mappers.CommentListMapper;
import com.example.scame.lighttube.data.rest.CommentsApi;
import com.example.scame.lighttube.presentation.model.CommentListModel;

import rx.Observable;

public class CommentsDataManagerImp implements ICommentsDataManager {

    private static final String PART = "snippet%2Creplies";

    private static final int MAX_RES = 50;

    private static final String TEXT_FORMAT = "plainText";

    private CommentsApi commentsApi;

    private CommentListMapper commentListMapper;

    public CommentsDataManagerImp(CommentsApi commentsApi, CommentListMapper commentListMapper) {
        this.commentListMapper = commentListMapper;
        this.commentsApi = commentsApi;
    }

    @Override
    public Observable<CommentListModel> getCommentList(String videoId) {
        return commentsApi.getCommentThreads(PART, MAX_RES, null, TEXT_FORMAT, videoId, PrivateValues.API_KEY)
                .map(commentListMapper::convert);
    }
}
