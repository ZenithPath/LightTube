package com.example.scame.lighttube.presentation.adapters.player;


import android.support.v7.widget.PopupMenu;
import android.util.Pair;
import android.view.View;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;

public class PopupHandler {

    private String identifier;

    private CommentActionListener commentActionListener;

    public PopupHandler(CommentActionListener commentActionListener, String identifier) {
        this.identifier = identifier;
        this.commentActionListener = commentActionListener;
    }

    public void showPopup(View anchor, String userIdentifier, String commentId, Pair<Integer, Integer> commentIndex) {
        if (userIdentifier.equals(identifier)) {
            showAuthoredPopup(anchor, commentId, commentIndex);
        } else {
            showCommonPopup(anchor, commentId, commentIndex);
        }
    }

    private void showAuthoredPopup(View anchor, String commentId, Pair<Integer, Integer> commentIndex) {
        PopupMenu authoredPopup = new PopupMenu(anchor.getContext(), anchor);
        authoredPopup.inflate(R.menu.comment_popup);
        authoredPopup.getMenu().removeItem(R.id.report_option);
        authoredPopup.getMenu().removeItem(R.id.reply_option);

        authoredPopup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.delete_option) {
                commentActionListener.onActionDeleteClick(commentId, commentIndex);
            } else if (item.getItemId() == R.id.edit_option) {
                commentActionListener.onActionEditClick(commentId, commentIndex);
            }
            return false;
        });

        authoredPopup.show();
    }

    private void showCommonPopup(View anchor, String commentId, Pair<Integer, Integer> commentIndex) {
        PopupMenu commonPopup = new PopupMenu(anchor.getContext(), anchor);
        commonPopup.inflate(R.menu.comment_popup);
        commonPopup.getMenu().removeItem(R.id.delete_option);
        commonPopup.getMenu().removeItem(R.id.edit_option);

        commonPopup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.report_option) {
                commentActionListener.onActionMarkAsSpamClick(commentId, commentIndex);
            } else if (item.getItemId() == R.id.reply_option) {
                commentActionListener.onActionReplyClick(commentId, commentIndex);
            }

            return false;
        });

        commonPopup.show();
    }
}
