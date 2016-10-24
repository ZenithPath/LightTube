package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.view.View;

import com.example.scame.lighttube.presentation.fragments.CommentActionListener;
import com.example.scame.lighttube.presentation.fragments.PlayerFooterFragment;

public class MultipleRepliesHolder extends CommentsViewHolder {

    public MultipleRepliesHolder(PlayerFooterFragment.PlayerFooterListener footerListener,
                                 CommentActionListener actionListener, String identifier, View itemView) {
        super(footerListener, itemView, identifier, actionListener);
    }
}
