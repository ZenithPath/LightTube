package com.example.scame.lighttube.presentation.adapters.player.replies;


import com.example.scame.lighttube.presentation.model.ReplyModel;

public class UpdateReplyModelHolder extends ReplyModel {

    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
