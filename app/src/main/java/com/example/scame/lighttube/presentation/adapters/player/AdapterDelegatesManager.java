package com.example.scame.lighttube.presentation.adapters.player;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface AdapterDelegatesManager<T> {

    AdapterDelegatesManager<T> addDelegate(@NonNull AdapterDelegate<T> delegate);

    int getItemViewType(@NonNull T items, int position);

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(@NonNull T items, int position, @NonNull RecyclerView.ViewHolder viewHolder);

    int getItemCount(@NonNull T items);
}
