package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.ProgressViewHolder;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;

import java.util.List;

public class ProgressDelegate implements AdapterDelegate<List<?>> {

    private int viewType;

    public ProgressDelegate(int viewType) {
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
        return new ProgressViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull List<?> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof ProgressViewHolder) {
            ((ProgressViewHolder) holder).bindProgressView();
        }
    }

    @Override
    public int getViewType() {
        return viewType;
    }
}
