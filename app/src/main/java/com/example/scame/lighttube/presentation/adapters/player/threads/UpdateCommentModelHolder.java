package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.util.Pair;

import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

public class UpdateCommentModelHolder extends ThreadCommentModel {

    public UpdateCommentModelHolder() { }

    // don't need this in case of thread comments editing,
    // but if target is a reply, then the info would be lost
    public UpdateCommentModelHolder(ThreadCommentModel threadCommentModel) {
        super(threadCommentModel);
    }

    private Pair<Integer, Integer> pairedPosition;

    public void setPairedPosition(Pair<Integer, Integer> pairedPosition) {
        this.pairedPosition = pairedPosition;
    }

    public Pair<Integer, Integer> getPairedPosition() {
        return pairedPosition;
    }
}
