package com.example.scame.lighttube.presentation.model;


import java.util.ArrayList;
import java.util.List;

public class CommentListModel {

    private List<ThreadCommentModel> threadComments;

    public CommentListModel() {
        threadComments = new ArrayList<>();
    }

    public void addThreadCommentModel(ThreadCommentModel threadCommentModel) {
        threadComments.add(threadCommentModel);
    }

    public void setThreadComments(List<ThreadCommentModel> threadComments) {
        this.threadComments = threadComments;
    }

    public List<ThreadCommentModel> getThreadComments() {
        return threadComments;
    }
}
