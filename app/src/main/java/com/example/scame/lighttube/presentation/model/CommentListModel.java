package com.example.scame.lighttube.presentation.model;


import android.util.Pair;

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

    public void addThreadCommentModel(int index, ThreadCommentModel threadCommentModel) {
        threadComments.add(index, threadCommentModel);
    }

    public void setThreadComments(List<ThreadCommentModel> threadComments) {
        this.threadComments = threadComments;
    }

    public List<ThreadCommentModel> getThreadComments() {
        return threadComments;
    }

    public void deleteByPairIndex(Pair<Integer, Integer> pairIndex) {
        if (pairIndex.second == -1) {
            threadComments.remove(+pairIndex.first);
        } else {
            ThreadCommentModel threadCommentModel = threadComments.get(+pairIndex.first);
            threadCommentModel.getReplies().remove(+pairIndex.second);
        }
    }
}
