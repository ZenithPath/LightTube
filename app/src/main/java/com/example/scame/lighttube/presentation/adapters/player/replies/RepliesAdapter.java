package com.example.scame.lighttube.presentation.adapters.player.replies;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.scame.lighttube.presentation.model.ReplyListModel;

// TODO: generify dataset
public class RepliesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RepliesDelegatesManager delegatesManager;

    private ReplyListModel dataset;

    public RepliesAdapter(RepliesDelegatesManager delegatesManager, ReplyListModel dataset) {
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
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(dataset, position);
    }

    @Override
    public int getItemCount() {
        return delegatesManager.getItemCount(dataset);
    }
}
