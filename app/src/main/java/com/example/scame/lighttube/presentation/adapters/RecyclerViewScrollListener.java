package com.example.scame.lighttube.presentation.adapters;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.scame.lighttube.presentation.ConnectivityReceiver;

public class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 3;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

    private boolean connectedPreviously = true;

    private int currentPage;

    private LinearLayoutManager layoutManager;

    private OnLoadMoreListener onLoadMoreListener;

    private NoConnectionListener noConnectionListener;

    private DirectionScrollListener directionScrollListener;

    public interface DirectionScrollListener {

        void onDirectionScroll(boolean scrollToTop);
    }

    public RecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public RecyclerViewScrollListener(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        notifyScrollDirection(dx, dy);

        totalItemCount = layoutManager.getItemCount();
        lastVisibleItem = layoutManager.findLastVisibleItemPosition();

        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold) &&
                ConnectivityReceiver.isConnected() && connectedPreviously) {

            if (onLoadMoreListener != null) {
                onLoadMoreListener.onLoadMore(++currentPage);
            }

            loading = true;
        } else if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold) &&
                !ConnectivityReceiver.isConnected() && connectedPreviously) {

            if (noConnectionListener != null) {
                noConnectionListener.onNoConnection();
            }

            connectedPreviously = false;
        }
    }

    private void notifyScrollDirection(int dx, int dy) {

        if (directionScrollListener != null) {
            if (dy < 0) { // scrolled up
                directionScrollListener.onDirectionScroll(true);
            } else if (dy > 0) { // scrolled down
                directionScrollListener.onDirectionScroll(false);
            }
        }
    }

    public void setDirectionScrollListener(DirectionScrollListener directionScrollListener) {
        this.directionScrollListener = directionScrollListener;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void setConnectedPreviously(boolean connectedPreviously) {
        this.connectedPreviously = connectedPreviously;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setNoConnectionListener(NoConnectionListener noConnectionListener) {
        this.noConnectionListener = noConnectionListener;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isConnectedPreviously() {
        return connectedPreviously;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}

