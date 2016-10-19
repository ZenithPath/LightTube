package com.example.scame.lighttube.presentation.fragments;


import android.util.Pair;

public interface CommentActionListener {

    void onDeleteClick(String commentId, Pair<Integer, Integer> commentIndex);

    void onUpdateClick(String commentId, Pair<Integer, Integer> commentIndex, String updatedText);

    void onMarkAsSpamClick(String commentId, Pair<Integer, Integer> commentIndex);
}
