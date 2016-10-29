package com.example.scame.lighttube.utility;


import android.util.Pair;

import com.example.scame.lighttube.presentation.adapters.player.replies.RepliesDelegatesManager;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.List;

public class CommentsUtility {

    public static int getFilteredIndex(String commentId, List<?> dataset) {
        Pair<Integer, Integer> pairedIndex = CommentsUtility.getReplyIndexById(commentId, dataset);
        return pairedIndex.first == -1 ? pairedIndex.second : pairedIndex.first;
    }
    
    public static Pair<Integer, Integer> getReplyIndexById(String commentId, List<?> dataset) {
        Pair<Integer, Integer> primary = checkPrimaryComment(commentId, dataset);
        if (primary != null) {
            return primary;
        } else {
            return checkReplies(commentId, dataset);
        }
    }

    private static Pair<Integer, Integer> checkPrimaryComment(String commentId, List<?> dataset) {
        if (dataset.get(RepliesDelegatesManager.HEADER_COMMENT_POS) instanceof ThreadCommentModel) {
            ThreadCommentModel threadCommentModel = (ThreadCommentModel) dataset
                    .get(RepliesDelegatesManager.HEADER_COMMENT_POS);
            if (threadCommentModel.getThreadId().equals(commentId)) {
                return new Pair<>(RepliesDelegatesManager.HEADER_COMMENT_POS, -1);
            }
        }
        return null;
    }

    private static Pair<Integer, Integer> checkReplies(String commentId, List<?> dataset) {
        for (int i = 0; i < dataset.size(); i++) {
            if (dataset.get(i) instanceof ReplyModel) {
                if (((ReplyModel) dataset.get(i)).getCommentId().equals(commentId)) {
                    return new Pair<>(-1, i);
                }
            }
        }
        return null;
    }
}
