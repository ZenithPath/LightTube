package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;

import java.util.List;

public class SingleReplyDelegate implements AdapterDelegate<List<?>> {

    private CommentActionListener actionListener;

    private String identifier;

    public SingleReplyDelegate(CommentActionListener actionListener, String identifier) {
        this.actionListener = actionListener;
        this.identifier = identifier;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_group_item, parent, false);
        return new SingleReplyHolder(itemView, actionListener, identifier);
    }

    @Override
    public void onBindViewHolder(@NonNull List<?> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof SingleReplyHolder) {
            ((SingleReplyHolder) holder).bindOneReplyView(position, items);
        }
    }

    @Override
    public int getViewType() {
        return CommentsDelegatesManager.VIEW_TYPE_ONE_REPLY;
    }
}
