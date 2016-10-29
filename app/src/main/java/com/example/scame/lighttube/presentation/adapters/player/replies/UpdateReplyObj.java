package com.example.scame.lighttube.presentation.adapters.player.replies;


import android.util.Pair;

public class UpdateReplyObj {

    private Pair<Integer, Integer> position;

    private String commentId;

    private String textToUpdate;

    public UpdateReplyObj(Pair<Integer, Integer> position, String commentId, String textToUpdate) {
        this.position = position;
        this.commentId = commentId;
        this.textToUpdate = textToUpdate;
    }

    public UpdateReplyObj() { }

    public void setTextToUpdate(String textToUpdate) {
        this.textToUpdate = textToUpdate;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public void setPosition(Pair<Integer, Integer> position) {
        this.position = position;
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getTextToUpdate() {
        return textToUpdate;
    }
}
