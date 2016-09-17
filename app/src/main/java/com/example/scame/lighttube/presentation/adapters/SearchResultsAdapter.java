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
import com.example.scame.lighttube.presentation.model.SearchItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_PROGRESS = 0;
    private static final int VIEW_TYPE_VIDEO = 1;
    private static final int VIEW_TYPE_NO_CONNECTION = 2;

    private Context context;
    private List<?> searchItems;

    private static OnItemClickListener listener;

    private NoConnectionViewHolder.OnRetryClickListener onRetryClickListener;

    private RecyclerViewScrollListener scrollListener;

    public static class SearchViewHolder extends  RecyclerView.ViewHolder {

        @BindView(R.id.search_item_iv) ImageView searchIv;
        @BindView(R.id.search_item_title) TextView titleTv;

        public SearchViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onItemClick(view, getLayoutPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public SearchResultsAdapter(List<?> items, Context context, RecyclerView recyclerView) {
        this.searchItems = items;
        this.context = context;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            scrollListener = new RecyclerViewScrollListener(linearLayoutManager);
            recyclerView.addOnScrollListener(scrollListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (searchItems.get(position) instanceof NoConnectionMarker) {
            return VIEW_TYPE_NO_CONNECTION;
        } else if (searchItems.get(position) instanceof SearchItemModel) {
            return VIEW_TYPE_VIDEO;
        } else if (searchItems.get(position) == null) {
            return VIEW_TYPE_PROGRESS;
        }

        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_VIDEO) {
            View videoView = inflater.inflate(R.layout.search_result_item, parent, false);
            viewHolder = new SearchViewHolder(videoView);
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

        if (holder instanceof SearchViewHolder) {
            SearchItemModel searchItem = (SearchItemModel) searchItems.get(position);
            SearchViewHolder viewHolder = (SearchViewHolder) holder;

            ImageView imageView = viewHolder.searchIv;
            TextView textView = viewHolder.titleTv;

            Picasso.with(context).load(searchItem.getImageUrl()).resize(500, 275).centerCrop().into(imageView);
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
        return searchItems.size();
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
        SearchResultsAdapter.listener = listener;
    }
}
