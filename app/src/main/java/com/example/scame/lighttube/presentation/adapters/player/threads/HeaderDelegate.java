package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.List;

public class HeaderDelegate implements AdapterDelegate<List<ThreadCommentModel>> {

    private Context context;

    private String videoId;

    private String videoTitle;

    public HeaderDelegate(Context context, String videoId, String videoTitle) {
        this.context = context;
        this.videoId = videoId;
        this.videoTitle = videoTitle;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_header_item, parent, false);
        return new HeaderHolder(headerView, context, videoId, videoTitle);
    }

    @Override
    public void onBindViewHolder(@NonNull List<ThreadCommentModel> items, int position, @NonNull RecyclerView.ViewHolder holder) {

    }

    @Override
    public int getViewType() {
        return CommentsDelegatesManager.VIEW_TYPE_HEADER;
    }
}
