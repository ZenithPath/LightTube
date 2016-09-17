package com.example.scame.lighttube.presentation.adapters;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.model.SearchItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_PROGRESS = 0;
    public static final int VIEW_TYPE_VIDEO = 1;
    public static final int VIEW_TYPE_NO_CONNECTION = 2;

    private Context context;

    private static OnItemClickListener listener;

    private NoConnectionViewHolder.OnRetryClickListener onRetryClickListener;

    private RecyclerViewScrollListener scrollListener;

    private List<?> items;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public GridAdapter(Context context, List<?> items, RecyclerView recyclerView) {
        this.context = context;
        this.items = items;

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

            scrollListener = new RecyclerViewScrollListener(gridLayoutManager);
            recyclerView.addOnScrollListener(scrollListener);
        }
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
        } else if (items.get(position) instanceof SearchItemModel) {
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
            SearchItemModel searchItem = (SearchItemModel) items.get(position);
            GridViewHolder gridVideoHolder = (GridViewHolder) holder;

            ImageView imageView = gridVideoHolder.thumbnails;
            TextView textView = gridVideoHolder.title;

            Picasso.with(context).load(searchItem.getImageUrl()).resize(300, 225).centerCrop().into(imageView);
            textView.setText(searchItem.getTitle());
        } else if (holder instanceof ProgressViewHolder) {
            ProgressViewHolder progressHolder = (ProgressViewHolder) holder;
            progressHolder.progressBar.setIndeterminate(true);
        } else if (holder instanceof NoConnectionViewHolder) {
            NoConnectionViewHolder noConnectionViewHolder = (NoConnectionViewHolder) holder;
            noConnectionViewHolder.setClickListener(onRetryClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
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

    public void setCurrentPage(int page) {
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
        GridAdapter.listener = listener;
    }
}
