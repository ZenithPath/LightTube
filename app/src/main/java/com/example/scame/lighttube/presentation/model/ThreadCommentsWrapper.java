package com.example.scame.lighttube.presentation.model;


import com.example.scame.lighttube.data.repository.CommentsDataManagerImp;

import java.util.List;

public class ThreadCommentsWrapper {

    private String commentsOrder;

    private List<ThreadCommentModel> comments;

    public ThreadCommentsWrapper() { }

    public ThreadCommentsWrapper(@CommentsDataManagerImp.CommentsOrders String commentsOrder,
                                 List<ThreadCommentModel> comments) {
        this.commentsOrder = commentsOrder;
        this.comments = comments;
    }

    public void setCommentsOrder(@CommentsDataManagerImp.CommentsOrders String commentsOrder) {
        this.commentsOrder = commentsOrder;
    }

    public void setComments(List<ThreadCommentModel> comments) {
        this.comments = comments;
    }

    public @CommentsDataManagerImp.CommentsOrders String getCommentsOrder() {
        return commentsOrder;
    }

    public List<ThreadCommentModel> getComments() {
        return comments;
    }
}
