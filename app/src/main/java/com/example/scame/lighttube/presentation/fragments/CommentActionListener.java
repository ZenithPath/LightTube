package com.example.scame.lighttube.presentation.fragments;


import android.util.Pair;

import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

public interface CommentActionListener {

    void onDeleteClick(String commentId, Pair<Integer, Integer> commentIndex);

    void onUpdateClick(String commentId, Pair<Integer, Integer> commentIndex);

    void onMarkAsSpamClick(String commentId, Pair<Integer, Integer> commentIndex);

    void onPostedComment(ThreadCommentModel threadCommentModel);
}
