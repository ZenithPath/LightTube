package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.fragments.PlayerFooterFragment;

import java.util.List;

public class CommentInputDelegate implements AdapterDelegate<List<?>> {

    private PlayerFooterFragment.PostedCommentListener postedCommentListener;

    public CommentInputDelegate(PlayerFooterFragment.PostedCommentListener postedCommentListener) {
        this.postedCommentListener = postedCommentListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_input_item, parent, false);
        return new CommentInputViewHolder(postedCommentListener, itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull List<?> items, int position, @NonNull RecyclerView.ViewHolder holder) {

    }

    @Override
    public int getViewType() {
        return CommentsDelegatesManager.VIEW_TYPE_COMMENT_INPUT;
    }
}
