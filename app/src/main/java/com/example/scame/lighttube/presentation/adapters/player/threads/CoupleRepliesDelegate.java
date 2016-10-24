package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.List;

public class CoupleRepliesDelegate implements AdapterDelegate<List<ThreadCommentModel>> {

    private CommentActionListener actionListener;

    private String identifier;

    public CoupleRepliesDelegate(CommentActionListener actionListener, String identifier) {
        this.actionListener = actionListener;
        this.identifier = identifier;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_group_item, parent, false);
        return new CoupleRepliesHolder(itemView, actionListener, identifier);
    }

    @Override
    public void onBindViewHolder(@NonNull List<ThreadCommentModel> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof CoupleRepliesHolder) {
            ((CoupleRepliesHolder) holder).bindTwoRepliesView(position, items);
        }
    }

    @Override
    public int getViewType() {
        return CommentsDelegatesManager.VIEW_TYPE_TWO_REPLIES;
    }
}
