package com.example.scame.lighttube.presentation.adapters;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_PROGRESS = 0;
    public static final int VIEW_TYPE_VIDEO = 1;
    public static final int VIEW_TYPE_NO_CONNECTION = 2;

    protected List<?> items;
    protected Context context;

    protected NoConnectionViewHolder.OnRetryClickListener onRetryClickListener;

    protected RecyclerViewScrollListener scrollListener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public BaseAdapter(RecyclerView recyclerView, Context context, List<?> items) {
        this.context = context;
        this.items = items;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            scrollListener = new RecyclerViewScrollListener(gridLayoutManager);
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            scrollListener = new RecyclerViewScrollListener(linearLayoutManager);
        }

        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Context getContext() {
        return context;
    }

    public boolean isLoading() {
        return scrollListener.isLoading();
    }

    public boolean isConnectedPreviously() {
        return scrollListener.isConnectedPreviously();
    }

    public void setConnectedPreviously(boolean connectedPreviously) {
        scrollListener.setConnectedPreviously(connectedPreviously);
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
}
