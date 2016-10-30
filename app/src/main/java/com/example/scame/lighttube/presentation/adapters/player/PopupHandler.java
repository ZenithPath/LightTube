package com.example.scame.lighttube.presentation.adapters.player;


import android.support.v7.widget.PopupMenu;
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

    public void showPopup(View anchor, String userIdentifier, String commentId) {
        if (userIdentifier.equals(identifier)) {
            showAuthoredPopup(anchor, commentId);
        } else {
            showCommonPopup(anchor, commentId);
        }
    }

    private void showAuthoredPopup(View anchor, String commentId) {
        PopupMenu authoredPopup = new PopupMenu(anchor.getContext(), anchor);
        authoredPopup.inflate(R.menu.comment_popup);
        authoredPopup.getMenu().removeItem(R.id.report_option);
        authoredPopup.getMenu().removeItem(R.id.reply_option);

        authoredPopup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.delete_option) {
                commentActionListener.onActionDeleteClick(commentId);
            } else if (item.getItemId() == R.id.edit_option) {
                commentActionListener.onActionEditClick(commentId);
            }
            return false;
        });

        authoredPopup.show();
    }

    private void showCommonPopup(View anchor, String commentId) {
        PopupMenu commonPopup = new PopupMenu(anchor.getContext(), anchor);
        commonPopup.inflate(R.menu.comment_popup);
        commonPopup.getMenu().removeItem(R.id.delete_option);
        commonPopup.getMenu().removeItem(R.id.edit_option);

        commonPopup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.report_option) {
                commentActionListener.onActionMarkAsSpamClick(commentId);
            } else if (item.getItemId() == R.id.reply_option) {
                commentActionListener.onActionReplyClick(commentId);
            }

            return false;
        });

        commonPopup.show();
    }
}
