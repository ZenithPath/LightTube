package com.example.scame.lighttube.presentation.fragments;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.repository.PaginationUtility;
import com.example.scame.lighttube.presentation.activities.TabActivity;
import com.example.scame.lighttube.presentation.adapters.BaseAdapter;
import com.example.scame.lighttube.presentation.adapters.GridAdapter;
import com.example.scame.lighttube.presentation.model.VideoModel;
import com.example.scame.lighttube.presentation.presenters.GridPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.State;

public class GridFragment extends BaseFragment implements GridPresenter.GridView, ScrollingHelperListener {

    private static final int FIRST_PAGE_INDEX = 0;

    @BindView(R.id.grid_rv) RecyclerView gridRv;

    @BindView(R.id.grid_toolbar) Toolbar gridToolbar;

    @BindView(R.id.grid_pb) ProgressBar progressBar;

    @BindView(R.id.grid_swipe) SwipeRefreshLayout refreshLayout;

    @Inject
    GridPresenter<GridPresenter.GridView> presenter;

    @Inject
    @Named("general")
    PaginationUtility paginationUtility;

    @State ArrayList<Object> items;

    private BaseAdapter gridAdapter;

    private String duration;
    private String category;

    private GridFragmentListener gridFragmentListener;

    private RecyclerScrollingHelper scrollingHelper;

    private Bundle savedInstanceState;

    public interface GridFragmentListener {

        void onVideoClick(VideoModel videoModel);

        void onScrolled(boolean scrolledToTop);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof GridFragmentListener) {
            gridFragmentListener = (GridFragmentListener) context;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(gridToolbar);
    }

    @Override
    public void onPageChange(int pageNumber) {
        presenter.fetchVideos(category, duration, pageNumber);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.grid_fragment, container, false);

        inject();
        this.savedInstanceState = savedInstanceState;
        ButterKnife.bind(this, fragmentView);
        presenter.setView(this);

        parseVideosSpecification();

        progressBar.setVisibility(View.VISIBLE);
        gridRv.setVisibility(View.GONE);

        instantiateFragment(savedInstanceState);

        return fragmentView;
    }

    private void instantiateFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null && items != null){
            initializeAdapter(items);
        } else {
            presenter.fetchVideos(category, duration, FIRST_PAGE_INDEX);
        }
    }

    private void parseVideosSpecification() {
        Bundle args = getArguments();
        duration = args.getString(getString(R.string.duration_key));
        category = args.getString(getString(R.string.category_key));
    }

    private void inject() {
        if (getActivity() instanceof TabActivity) {
            TabActivity tabActivity = (TabActivity) getActivity();
            tabActivity.getGridComponent().inject(this);
        }
    }

    @Override
    public void initializeAdapter(List<?> newItems) {
        items = new ArrayList<>(newItems);

        progressBar.setVisibility(View.GONE);
        gridRv.setVisibility(View.VISIBLE);

        gridRv.setLayoutManager(buildLayoutManager());

        gridAdapter = new GridAdapter(getContext(), items, gridRv);
        gridAdapter.setPaginationUtility(paginationUtility);

        initializeScrollingHelper();
        setupListeners();

        gridRv.setAdapter(gridAdapter);
        refreshLayout.setRefreshing(false);
    }

    private void initializeScrollingHelper() {
        scrollingHelper = new RecyclerScrollingHelper(items, gridAdapter, refreshLayout, this);
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
        setupDirectionScrollListener();
    }

    private void setupDirectionScrollListener() {
        gridAdapter.setDirectionScrollListener(scrollToTop -> gridFragmentListener.onScrolled(scrollToTop));
    }


    private void setupOnVideoClickListener() {
        if (gridAdapter instanceof GridAdapter) {
            GridAdapter adapter = (GridAdapter) gridAdapter;
            adapter.setupOnItemClickListener((itemView, position) -> {
                VideoModel videoModel = ((VideoModel) items.get(position));
                gridFragmentListener.onVideoClick(videoModel);
            });
        }
    }

    @Override
    public void updateAdapter(List<?> newItems) {
        items.remove(items.size() - 1);
        gridAdapter.notifyItemRemoved(items.size());

        items.addAll(newItems);
        gridAdapter.notifyItemRangeInserted(gridAdapter.getItemCount(), newItems.size());

        gridAdapter.setLoading(false);
    }


    private GridLayoutManager buildLayoutManager() {
        int columnNumber;

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columnNumber = 3;
        } else {
            columnNumber = 2;
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), columnNumber);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                switch (gridAdapter.getItemViewType(position)) {
                    case GridAdapter.VIEW_TYPE_VIDEO:
                        return 1;
                    case GridAdapter.VIEW_TYPE_PROGRESS:
                        return columnNumber;
                    case GridAdapter.VIEW_TYPE_NO_CONNECTION:
                        return columnNumber;
                    default:
                        return -1;
                }
            }
        });

        return gridLayoutManager;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (scrollingHelper != null) {
            scrollingHelper.onSaveInstanceState(outState);
        }
    }

    public void scrollToTop() {
        gridRv.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
