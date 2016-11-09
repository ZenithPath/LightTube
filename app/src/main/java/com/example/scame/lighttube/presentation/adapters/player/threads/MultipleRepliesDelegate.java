package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;
import com.example.scame.lighttube.presentation.fragments.PlayerFooterFragment;

import java.util.List;

public class MultipleRepliesDelegate implements AdapterDelegate<List<?>> {

    private PlayerFooterFragment.PlayerFooterListener footerListener;

    private CommentActionListener actionListener;

    private String userIdentifier;

    private int viewType;

    public MultipleRepliesDelegate(PlayerFooterFragment.PlayerFooterListener footerListener,
                                   CommentActionListener actionListener, String userIdentifier,
                                   int viewType) {
        this.footerListener = footerListener;
        this.actionListener = actionListener;
        this.userIdentifier = userIdentifier;
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_group_item, parent, false);
        return new MultipleRepliesHolder(footerListener, actionListener, userIdentifier, itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull List<?> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof MultipleRepliesHolder) {
            ((MultipleRepliesHolder) holder).bindAllRepliesView(position, items);
        }
    }

    @Override
    public int getViewType() {
        return viewType;
    }
}
