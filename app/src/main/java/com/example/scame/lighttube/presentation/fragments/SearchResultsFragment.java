package com.example.scame.lighttube.presentation.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.ConnectivityReceiver;
import com.example.scame.lighttube.presentation.adapters.BaseAdapter;
import com.example.scame.lighttube.presentation.adapters.NoConnectionMarker;
import com.example.scame.lighttube.presentation.adapters.SearchResultsAdapter;
import com.example.scame.lighttube.presentation.di.components.SearchComponent;
import com.example.scame.lighttube.presentation.model.ModelMarker;
import com.example.scame.lighttube.presentation.model.VideoModel;
import com.example.scame.lighttube.presentation.presenters.ISearchResultsPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.State;

import static com.example.scame.lighttube.presentation.presenters.ISearchResultsPresenter.SearchResultsView;

public class SearchResultsFragment extends BaseFragment implements SearchResultsView {

    @BindView(R.id.search_results_rv) RecyclerView recyclerView;

    @BindView(R.id.search_results_pb) ProgressBar progressBar;

    @BindView(R.id.search_results_swipe) SwipeRefreshLayout refreshLayout;

    @Inject ISearchResultsPresenter<SearchResultsView> presenter;

    @State ArrayList<ModelMarker> searchItems;

    // these variables represent adapter state
    @State int currentPage;
    @State boolean isLoading;
    @State boolean isConnectedPreviously = true;

    private BaseAdapter adapter;

    private SearchResultsListener listener;

    private String query;

    public interface SearchResultsListener {

        void onVideoClick(String id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SearchResultsListener) {
            listener = (SearchResultsListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.search_results_fragment, container, false);

        ButterKnife.bind(this, fragmentView);
        getComponent(SearchComponent.class).inject(this);
        parseSearchQuery();

        presenter.setView(this);

        setupRefreshListener();

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        instantiateFragment(savedInstanceState);

        return fragmentView;
    }

    private void instantiateFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null && searchItems != null) {
            initializeAdapter(searchItems);
        } else {
            presenter.fetchVideos(currentPage, query);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        isLoading = adapter.isLoading();
        isConnectedPreviously = adapter.isConnectedPreviously();

        super.onSaveInstanceState(outState);
    }

    @Override
    public void initializeAdapter(List<? extends ModelMarker> items) {
        searchItems = new ArrayList<>(items);

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(buildLayoutManager());

        adapter = new SearchResultsAdapter(searchItems, getContext(), recyclerView);
        adapter.setCurrentPage(currentPage);
        adapter.setConnectedPreviously(isConnectedPreviously);
        adapter.setLoading(isLoading);

        setupRetryListener();
        setupOnVideoClickListener();
        setupOnLoadMoreListener();
        setupNoConnectionListener();

        recyclerView.setAdapter(adapter);

        stopRefreshing();
    }

    @Override
    public void updateAdapter(List<? extends ModelMarker> newItems) {
        searchItems.remove(searchItems.size() - 1);
        adapter.notifyItemRemoved(searchItems.size());

        searchItems.addAll(newItems);
        adapter.notifyItemRangeInserted(adapter.getItemCount(), newItems.size());

        adapter.setLoading(false);
    }

    private void setupRetryListener() {
        adapter.setOnRetryClickListener(() -> {

            if (ConnectivityReceiver.isConnected()) {
                stopRefreshing();

                searchItems.remove(searchItems.size() - 1);
                adapter.notifyItemRemoved(searchItems.size());

                searchItems.add(null);
                adapter.notifyItemInserted(searchItems.size() - 1);

                ++currentPage;
                adapter.setLoading(true);
                adapter.setConnectedPreviously(true);
                adapter.setCurrentPage(currentPage);
                presenter.fetchVideos(currentPage, query);
            }
        });
    }

    private void setupNoConnectionListener() {
        adapter.setNoConnectionListener(() -> {
            searchItems.add(new NoConnectionMarker());
            adapter.notifyItemInserted(searchItems.size() - 1);
        });
    }

    private void setupOnVideoClickListener() {
        if (adapter instanceof SearchResultsAdapter) {
            SearchResultsAdapter searchAdapter = (SearchResultsAdapter) adapter;
            searchAdapter.setupOnItemClickListener((itemView, position) -> {
                String videoId = ((VideoModel) searchItems.get(position)).getVideoId();
                listener.onVideoClick(videoId);
            });
        }
    }

    private void setupOnLoadMoreListener() {
        adapter.setOnLoadMoreListener(page -> {
            searchItems.add(null);
            adapter.notifyItemInserted(searchItems.size() - 1);

            currentPage = page;
            presenter.fetchVideos(currentPage, query);
        });
    }

    private void stopRefreshing() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    private void setupRefreshListener() {
        refreshLayout.setOnRefreshListener(() -> {

            currentPage = 0;
            isLoading = false;
            isConnectedPreviously = true;

            presenter.fetchVideos(currentPage, query);
        });
    }

    private void parseSearchQuery() {
        Bundle args = getArguments();
        query = args.getString(getString(R.string.search_query), "");
    }

    private LinearLayoutManager buildLayoutManager() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        } else {
            return new LinearLayoutManager(getContext());
        }
    }
}
