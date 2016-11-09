package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.model.HeaderModel;

import java.util.List;

public class HeaderDelegate implements AdapterDelegate<List<?>> {

    private Context context;

    private HeaderModel headerModel;

    private int viewType;

    public HeaderDelegate(Context context, HeaderModel headerModel, int viewType) {
        this.context = context;
        this.headerModel = headerModel;
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_header_item, parent, false);
        return new HeaderHolder(headerView, context, headerModel);
    }

    @Override
    public void onBindViewHolder(@NonNull List<?> items, int position, @NonNull RecyclerView.ViewHolder holder) {

    }

    @Override
    public int getViewType() {
        return viewType;
    }
}
