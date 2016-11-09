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
import com.example.scame.lighttube.data.repository.PaginationUtility;
import com.example.scame.lighttube.presentation.adapters.BaseAdapter;
import com.example.scame.lighttube.presentation.adapters.SearchResultsAdapter;
import com.example.scame.lighttube.presentation.di.components.SearchComponent;
import com.example.scame.lighttube.presentation.model.VideoModel;
import com.example.scame.lighttube.presentation.presenters.SearchResultsPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.State;

import static com.example.scame.lighttube.presentation.presenters.SearchResultsPresenter.SearchResultsView;

public class SearchResultsFragment extends BaseFragment implements SearchResultsView, ScrollingHelperListener {

    private static final int FIRST_PAGE_INDEX = 0;

    @BindView(R.id.search_results_rv) RecyclerView recyclerView;

    @BindView(R.id.search_results_pb) ProgressBar progressBar;

    @BindView(R.id.search_results_swipe) SwipeRefreshLayout refreshLayout;

    @Inject
    SearchResultsPresenter<SearchResultsView> presenter;

    @Inject
    @Named("general")
    PaginationUtility paginationUtility;

    @State ArrayList<Object> searchItems;

    private BaseAdapter adapter;

    private SearchResultsListener listener;

    private String query;

    private RecyclerScrollingHelper scrollingHelper;

    private Bundle savedInstanceState;

    public interface SearchResultsListener {

        void onVideoClick(VideoModel videoModel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SearchResultsListener) {
            listener = (SearchResultsListener) context;
        }
    }

    @Override
    public void onPageChange(int pageNumber) {
        presenter.fetchVideos(pageNumber, query);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.search_results_fragment, container, false);

        this.savedInstanceState = savedInstanceState;
        ButterKnife.bind(this, fragmentView);
        getComponent(SearchComponent.class).inject(this);
        parseSearchQuery();

        presenter.setView(this);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        instantiateFragment(savedInstanceState);

        return fragmentView;
    }

    private void instantiateFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null && searchItems != null) {
            initializeAdapter(searchItems);
        } else {
            presenter.fetchVideos(FIRST_PAGE_INDEX, query);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (scrollingHelper != null) {
            scrollingHelper.onSaveInstanceState(outState);
        }
    }

    @Override
    public void initializeAdapter(List<?> items) {
        searchItems = new ArrayList<>(items);

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(buildLayoutManager());

        adapter = new SearchResultsAdapter(searchItems, getContext(), recyclerView);
        adapter.setPaginationUtility(paginationUtility);

        initializeScrollingHelper();
        setupListeners();

        recyclerView.setAdapter(adapter);
        refreshLayout.setRefreshing(false);
    }

    private void initializeScrollingHelper() {
        scrollingHelper = new RecyclerScrollingHelper(searchItems, adapter, refreshLayout, this);
        scrollingHelper.setPaginationUtility(paginationUtility);

        if (savedInstanceState != null) {
            scrollingHelper.onRestoreInstanceState(savedInstanceState);
        }
    }

    private void setupListeners() {
        scrollingHelper.setupRetryListener();
        scrollingHelper.setupOnLoadMoreListener();
        scrollingHelper.setupNoConnectionListener();
        setupOnVideoClickListener();
    }

    @Override
    public void updateAdapter(List<?> newItems) {
        searchItems.remove(searchItems.size() - 1);
        adapter.notifyItemRemoved(searchItems.size());

        searchItems.addAll(newItems);
        adapter.notifyItemRangeInserted(adapter.getItemCount(), newItems.size());

        adapter.setLoading(false);
    }

    private void setupOnVideoClickListener() {
        if (adapter instanceof SearchResultsAdapter) {
            SearchResultsAdapter searchAdapter = (SearchResultsAdapter) adapter;
            searchAdapter.setupOnItemClickListener((itemView, position) -> {
                VideoModel videoModel = ((VideoModel) searchItems.get(position));
                listener.onVideoClick(videoModel);
            });
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
