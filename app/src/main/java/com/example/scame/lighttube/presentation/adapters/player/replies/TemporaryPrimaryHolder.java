package com.example.scame.lighttube.presentation.adapters.player.replies;


import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

public class TemporaryPrimaryHolder extends ReplyModel {

    private ThreadCommentModel threadCommentModel;

    public TemporaryPrimaryHolder(ThreadCommentModel threadCommentModel) {
        this.threadCommentModel = threadCommentModel;
    }

    public void setThreadCommentModel(ThreadCommentModel threadCommentModel) {
        this.threadCommentModel = threadCommentModel;
    }

    public ThreadCommentModel getThreadCommentModel() {
        return threadCommentModel;
    }
}
