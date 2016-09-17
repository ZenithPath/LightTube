package com.example.scame.lighttube.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.model.VideoItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_PROGRESS = 0;
    private static final int VIEW_TYPE_VIDEO = 1;
    private static final int VIEW_TYPE_NO_CONNECTION = 2;

    private List<?> items;
    private Context context;

    private static OnItemClickListener listener;

    private NoConnectionViewHolder.OnRetryClickListener onRetryClickListener;

    private RecyclerViewScrollListener scrollListener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public VideoListAdapter(List<?> items, Context context, RecyclerView recyclerView) {
        this.items = items;
        this.context = context;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            scrollListener = new RecyclerViewScrollListener(linearLayoutManager);
            recyclerView.addOnScrollListener(scrollListener);
        }
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.videolist_item_iv) ImageView thumbnailsIv;
        @BindView(R.id.videolist_item_title) TextView titleTv;

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
        if (items.get(position) instanceof NoConnectionObject) {
            return VIEW_TYPE_NO_CONNECTION;
        } else if (items.get(position) instanceof VideoItemModel) {
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
            VideoItemModel videoItem = (VideoItemModel) items.get(position);

            videoViewHolder.titleTv.setText(videoItem.getTitle());
            Picasso.with(context)
                    .load(videoItem.getImageUrl())
                    .resize(650, 400).centerCrop()
                    .into(videoViewHolder.thumbnailsIv);
        } else if (holder instanceof ProgressViewHolder) {
            ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.progressBar.setIndeterminate(true);
        } else if (holder instanceof NoConnectionViewHolder) {
            NoConnectionViewHolder noConnectionViewHolder = (NoConnectionViewHolder) holder;
            noConnectionViewHolder.setClickListener(onRetryClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public Context getContext() {
        return context;
    }

    public void setConnectedPreviously(boolean connectedPreviously) {
        scrollListener.setConnectedPreviously(connectedPreviously);
    }

    public boolean isLoading() {
        return scrollListener.isLoading();
    }

    public boolean isConnectedPreviously() {
        return scrollListener.isConnectedPreviously();
    }

    public void setLoading(boolean isLoading) {
        scrollListener.setLoading(isLoading);
    }

    public void setPage(int page) {
        scrollListener.setCurrentPage(page);
    }

    public void setNoConnectionListener(NoConnectionListener noConnectionListener) {
        scrollListener.setNoConnectionListener(noConnectionListener);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        scrollListener.setOnLoadMoreListener(onLoadMoreListener);
    }

    public void setOnRetryClickListener(NoConnectionViewHolder.OnRetryClickListener onRetryClickListener) {
        this.onRetryClickListener = onRetryClickListener;
    }

    public void setupOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
