package com.example.scame.lighttube.presentation.fragments;


public interface CommentActionListener {

    void onActionDeleteClick(String commentId);

    void onActionMarkAsSpamClick(String commentId);

    void onActionReplyClick(String commentId);

    void onActionEditClick(String commentId);

    void onSendEditedClick(String commentText, String commentId);
}
