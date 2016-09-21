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

public class VideoListAdapter extends BaseAdapter {

    private static OnItemClickListener listener;

    public VideoListAdapter(List<?> items, Context context, RecyclerView recyclerView) {
        super(recyclerView, context, items);
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.video_list_item_iv) ImageView thumbnailsIv;
        @BindView(R.id.video_list_item_title) TextView titleTv;
        @BindView(R.id.video_list_item_duration) TextView durationTv;

        public VideoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onItemClick(view, getLayoutPosition());
                }
            });
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
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_VIDEO) {
            View viewItem = layoutInflater.inflate(R.layout.video_list_item, parent, false);
            viewHolder = new VideoViewHolder(viewItem);
        } else if (viewType == VIEW_TYPE_PROGRESS) {
            View viewItem = layoutInflater.inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressViewHolder(viewItem);
        } else if (viewType == VIEW_TYPE_NO_CONNECTION) {
            View viewItem = layoutInflater.inflate(R.layout.no_internet_list_item, parent, false);
            viewHolder = new NoConnectionViewHolder(viewItem);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof VideoViewHolder) {
            VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
            VideoModel videoModel = (VideoModel) items.get(position);

            videoViewHolder.titleTv.setText(videoModel.getTitle());
            videoViewHolder.durationTv.setText(videoModel.getDuration());

            Picasso.with(context).load(videoModel.getImageUrl())
                    .placeholder(R.drawable.colors_0011_pearl_grey)
                    .resize(650, 400)
                    .centerCrop()
                    .into(videoViewHolder.thumbnailsIv);

        } else if (holder instanceof ProgressViewHolder) {
            ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.progressBar.setIndeterminate(true);
        } else if (holder instanceof NoConnectionViewHolder) {
            NoConnectionViewHolder noConnectionViewHolder = (NoConnectionViewHolder) holder;
            noConnectionViewHolder.setClickListener(onRetryClickListener);
        }
    }

    public void setupOnItemClickListener(OnItemClickListener listener) {
        VideoListAdapter.listener = listener;
    }
}
