package com.example.scame.lighttube.presentation.fragments;


import android.util.Pair;

public interface CommentActionListener {

    void onActionDeleteClick(String commentId, Pair<Integer, Integer> commentIndex);

    void onActionMarkAsSpamClick(String commentId, Pair<Integer, Integer> commentIndex);

    void onActionReplyClick(String commentId, Pair<Integer, Integer> commentIndex);

    void onActionEditClick(String commentId, Pair<Integer, Integer> commentIndex);

    void onSendEditedClick(Pair<Integer, Integer> commentIndex, String commentText, String commentId);
}
