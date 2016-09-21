package com.example.scame.lighttube.presentation.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.model.VideoModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridAdapter extends BaseAdapter {

    private static BaseAdapter.OnItemClickListener listener;

    public GridAdapter(Context context, List<?> items, RecyclerView recyclerView) {
        super(recyclerView, context, items);
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.grid_item_iv) ImageView thumbnails;
        @BindView(R.id.grid_item_tv) TextView title;

        public GridViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v ->
                listener.onItemClick(itemView, getLayoutPosition())
            );

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof NoConnectionMarker) {
            return VIEW_TYPE_NO_CONNECTION;
        } else if (items.get(position) instanceof VideoModel) {
            return VIEW_TYPE_VIDEO;
        } else if (items.get(position) == null) {
            return VIEW_TYPE_PROGRESS;
        }

        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_VIDEO) {
            View videoView = inflater.inflate(R.layout.grid_item, parent, false);
            viewHolder = new GridViewHolder(videoView);
        } else if (viewType == VIEW_TYPE_PROGRESS) {
            View progressView = inflater.inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressViewHolder(progressView);
        } else if (viewType == VIEW_TYPE_NO_CONNECTION) {
            View noConnectionView = inflater.inflate(R.layout.no_internet_list_item, parent, false);
            viewHolder = new NoConnectionViewHolder(noConnectionView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof GridViewHolder) {
            VideoModel videoModel = (VideoModel) items.get(position);
            GridViewHolder gridVideoHolder = (GridViewHolder) holder;

            ImageView imageView = gridVideoHolder.thumbnails;
            TextView textView = gridVideoHolder.title;

            textView.setText(videoModel.getTitle());

            Picasso.with(context).load(videoModel.getImageUrl())
                    .placeholder(R.drawable.colors_0011_pearl_grey)
                    .resize(300, 225)
                    .centerCrop()
                    .into(imageView);

        } else if (holder instanceof ProgressViewHolder) {
            ProgressViewHolder progressHolder = (ProgressViewHolder) holder;
            progressHolder.progressBar.setIndeterminate(true);
        } else if (holder instanceof NoConnectionViewHolder) {
            NoConnectionViewHolder noConnectionViewHolder = (NoConnectionViewHolder) holder;
            noConnectionViewHolder.setClickListener(onRetryClickListener);
        }
    }

    public void setupOnItemClickListener(OnItemClickListener listener) {
        GridAdapter.listener = listener;
    }
}
