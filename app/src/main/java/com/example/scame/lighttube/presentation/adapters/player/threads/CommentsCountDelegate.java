package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;

import java.util.List;

import static com.example.scame.lighttube.presentation.adapters.player.threads.CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE;
import static com.example.scame.lighttube.presentation.adapters.player.threads.CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE_COUNTS;

public class CommentsCountDelegate implements AdapterDelegate<List<?>> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_count_item, parent, false);
        return new CommentsCountHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull List<?> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        int countsPosition = position + NUMBER_OF_VIEW_ABOVE - NUMBER_OF_VIEW_ABOVE_COUNTS;
        if (holder instanceof CommentsCountHolder) {
            ((CommentsCountHolder) holder).bindView(items, countsPosition);
        }
    }

    @Override
    public int getViewType() {
        return CommentsDelegatesManager.VIEW_TYPE_COMMENTS_COUNT;
    }
}
