package com.example.scame.lighttube.presentation.adapters.player;


import android.util.Pair;

public interface EditCommentListener {

    void onEditClick(Pair<Integer, Integer> commentIndex, String commentId);
}
