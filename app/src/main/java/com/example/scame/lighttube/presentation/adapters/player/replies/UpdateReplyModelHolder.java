package com.example.scame.lighttube.presentation.adapters.player.replies;


import android.util.Pair;

import com.example.scame.lighttube.presentation.model.ReplyModel;

public class UpdateReplyModelHolder extends ReplyModel {

    private Pair<Integer, Integer> position;

    public void setPosition(Pair<Integer, Integer> position) {
        this.position = position;
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }
}
