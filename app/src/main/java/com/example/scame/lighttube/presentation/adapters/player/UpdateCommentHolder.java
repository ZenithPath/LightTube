package com.example.scame.lighttube.presentation.adapters.player;


import android.util.Pair;

import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

public class UpdateCommentHolder extends ThreadCommentModel {

    private Pair<Integer, Integer> pairedPosition;

    public void setPairedPosition(Pair<Integer, Integer> pairedPosition) {
        this.pairedPosition = pairedPosition;
    }

    public Pair<Integer, Integer> getPairedPosition() {
        return pairedPosition;
    }
}
