package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private CommentsDelegatesManager delegatesManager;

    private List<ThreadCommentModel> dataset;

    public CommentsAdapter(CommentsDelegatesManager delegatesManager, List<ThreadCommentModel> dataset) {
        this.delegatesManager = delegatesManager;
        this.dataset = dataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         delegatesManager.onBindViewHolder(dataset, position, holder);
    }

    @Override
    public int getItemCount() {
        return delegatesManager.getItemCount(dataset);
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(dataset, position);
    }
}
