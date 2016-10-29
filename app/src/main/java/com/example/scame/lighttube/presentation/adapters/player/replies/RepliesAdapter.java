package com.example.scame.lighttube.presentation.adapters.player.replies;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

// TODO: generify dataset
public class RepliesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RepliesDelegatesManager delegatesManager;

    private List<?> dataset;

    public RepliesAdapter(RepliesDelegatesManager delegatesManager, List<?> dataset) {
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
