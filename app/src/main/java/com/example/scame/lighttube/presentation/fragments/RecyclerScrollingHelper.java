package com.example.scame.lighttube.presentation.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.repository.PaginationUtility;
import com.example.scame.lighttube.presentation.ConnectivityReceiver;
import com.example.scame.lighttube.presentation.adapters.BaseAdapter;
import com.example.scame.lighttube.presentation.adapters.NoConnectionMarker;

import java.util.List;

public class RecyclerScrollingHelper {

    private List<Object> items;

    private BaseAdapter adapter;

    private SwipeRefreshLayout refreshLayout;

    private ScrollingHelperListener scrollingListener;

    // these variables represent an adapter state
    int currentPage;
    boolean isLoading;
    boolean isConnectedPreviously = true;

    public RecyclerScrollingHelper(List<Object> items, BaseAdapter adapter, SwipeRefreshLayout refreshLayout,
                                   ScrollingHelperListener scrollingListener) {
        this.items = items;
        this.adapter = adapter;
        this.refreshLayout = refreshLayout;
        this.scrollingListener = scrollingListener;
    }

    public void setPaginationUtility(PaginationUtility paginationUtility) {
        adapter.setPaginationUtility(paginationUtility);
    }

    public void setupRetryListener() {
        adapter.setOnRetryClickListener(() -> {

            if (ConnectivityReceiver.isConnected()) {
                stopRefreshing();

                items.remove(items.size() - 1);
                adapter.notifyItemRemoved(items.size());

                items.add(null);
                adapter.notifyItemInserted(items.size() - 1);

                ++currentPage;
                adapter.setLoading(true);
                adapter.setConnectedPreviously(true);
                adapter.setCurrentPage(currentPage);
                if (scrollingListener != null) {
                    scrollingListener.onPageChange(currentPage);
                }
            }
        });
    }

    public void setupRefreshListener() {
        refreshLayout.setOnRefreshListener(() -> {
            currentPage = 0;
            isLoading = false;
            isConnectedPreviously = true;

            if (scrollingListener != null) {
                scrollingListener.onPageChange(currentPage);
            }
        });
    }

    public void setupNoConnectionListener() {
        adapter.setNoConnectionListener(() -> {
            items.add(new NoConnectionMarker());
            adapter.notifyItemInserted(items.size() - 1);
        });
    }

    public void setupOnLoadMoreListener() {
        adapter.setOnLoadMoreListener(page -> {
            items.add(null);
            adapter.notifyItemInserted(items.size() - 1);

            currentPage = page;
            if (scrollingListener != null) {
                scrollingListener.onPageChange(currentPage);
            }
        });
    }

    private void stopRefreshing() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        if (adapter != null) {
            Context context = adapter.getContext();
            isLoading = adapter.isLoading();
            isConnectedPreviously = adapter.isConnectedPreviously();

            outState.putInt(context.getString(R.string.adapter_page_key), currentPage);
            outState.putBoolean(context.getString(R.string.is_loading_key), isLoading);
            outState.putBoolean(context.getString(R.string.connected_previously_key), isConnectedPreviously);
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Context context = adapter.getContext();

        currentPage = savedInstanceState.getInt(context.getString(R.string.adapter_page_key), 0);
        isLoading = savedInstanceState.getBoolean(context.getString(R.string.is_loading_key), false);
        isConnectedPreviously = savedInstanceState.getBoolean(context.getString(R.string.connected_previously_key), true);
    }

    public void setScrollingListener(ScrollingHelperListener scrollingListener) {
        this.scrollingListener = scrollingListener;
    }
}
