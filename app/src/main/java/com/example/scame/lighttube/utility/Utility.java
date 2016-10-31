package com.example.scame.lighttube.utility;


import android.util.Pair;

import com.example.scame.lighttube.presentation.adapters.player.replies.RepliesDelegatesManager;
import com.example.scame.lighttube.presentation.adapters.player.threads.UpdateCommentObj;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.List;

public class Utility {

    public static final class RepliesUtil {

        public static int getFilteredIndex(String commentId, List<?> dataset) {
            Pair<Integer, Integer> pairedIndex = Utility.RepliesUtil.getReplyIndexById(commentId, dataset);
            return pairedIndex.first == -1 ? pairedIndex.second : pairedIndex.first;
        }

        public static Pair<Integer, Integer> getReplyIndexById(String commentId, List<?> dataset) {
            Pair<Integer, Integer> replyIndex = checkPrimaryComment(commentId, dataset);
            if (replyIndex == null) {
                replyIndex = checkReplies(commentId, dataset);
            }

            return replyIndex;
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

    public static final class FooterUtil {

        public static Pair<Integer, Integer> deleteById(List<?> dataset, String commentId) {
            Pair<Integer, Integer> index = getCommentIndexById(commentId, dataset);
            if (index.second == -1) {
                dataset.remove((int) index.first);
            } else {
                ThreadCommentModel threadCommentModel = getThreadModel(index.first, dataset);
                threadCommentModel.getReplies().remove((int) index.second);
            }

            return index;
        }

        public static int getFilteredIndex(String commentId, List<?> dataset) {
            Pair<Integer, Integer> pairedIndex = Utility.FooterUtil.getCommentIndexById(commentId, dataset);
            return pairedIndex.first == -1 ? pairedIndex.second : pairedIndex.first;
        }

        public static Pair<Integer, Integer> getCommentIndexById(String commentId, List<?> dataset) {
            for (int i = 0; i < dataset.size(); i++) {
                ThreadCommentModel threadCommentModel = getThreadModel(i, dataset);

                if (threadCommentModel != null) {
                    if (threadCommentModel.getThreadId().equals(commentId)) {
                        return new Pair<>(i, -1);
                    } else {
                        for (int j = 0; j < threadCommentModel.getReplies().size(); j++) {
                            if (threadCommentModel.getReplies().get(j).getCommentId().equals(commentId)) {
                                return new Pair<>(i, j);
                            }
                        }
                    }
                }
            }
            return null;
        }

        private static ThreadCommentModel getThreadModel(int index, List<?> dataset) {
            if (dataset.get(index) instanceof ThreadCommentModel) {
                return (ThreadCommentModel) dataset.get(index);
            } else if (dataset.get(index) instanceof UpdateCommentObj) {
                return  ((UpdateCommentObj) dataset.get(index)).getThreadCommentModel();
            }
            return null;
        }
    }
}
