package com.example.scame.lighttube.presentation.model;


import com.example.scame.lighttube.data.repository.CommentsRepositoryImp;

import java.util.List;

public class ThreadCommentsWrapper {

    private String commentsOrder;

    private List<ThreadCommentModel> comments;

    public ThreadCommentsWrapper(@CommentsRepositoryImp.CommentsOrders String commentsOrder,
                                 List<ThreadCommentModel> comments) {
        this.commentsOrder = commentsOrder;
        this.comments = comments;
    }

    public @CommentsRepositoryImp.CommentsOrders String getCommentsOrder() {
        return commentsOrder;
    }

    public List<ThreadCommentModel> getComments() {
        return comments;
    }
}
