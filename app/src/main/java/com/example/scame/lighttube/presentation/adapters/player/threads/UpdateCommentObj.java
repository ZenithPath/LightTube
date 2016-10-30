package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.util.Pair;

import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

public class UpdateCommentObj {

    private ThreadCommentModel threadCommentModel;

    private Pair<Integer, Integer> pairedPosition;

    public UpdateCommentObj() { }

    // don't need this in case of thread comments editing,
    // but if target is a reply, then the info would be lost
    public UpdateCommentObj(ThreadCommentModel threadCommentModel) {
        this.threadCommentModel = threadCommentModel;
    }

    public void setPairedPosition(Pair<Integer, Integer> pairedPosition) {
        this.pairedPosition = pairedPosition;
    }

    public Pair<Integer, Integer> getPairedPosition() {
        return pairedPosition;
    }

    public void setThreadCommentModel(ThreadCommentModel threadCommentModel) {
        this.threadCommentModel = threadCommentModel;
    }

    public ThreadCommentModel getThreadCommentModel() {
        return threadCommentModel;
    }
}
