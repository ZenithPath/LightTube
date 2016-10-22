package com.example.scame.lighttube.presentation.adapters.player;


import android.util.Pair;

public interface ReplyToIndividualListener {

    void onReplyToReplyClick(Pair<Integer, Integer> commentIndex, String commentId);
}
