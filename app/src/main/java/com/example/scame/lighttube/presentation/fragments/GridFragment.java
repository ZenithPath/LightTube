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
import com.example.scame.lighttube.presentation.activities.TabActivity;
import com.example.scame.lighttube.presentation.adapters.GridAdapter;
import com.example.scame.lighttube.presentation.model.SearchItemModel;
import com.example.scame.lighttube.presentation.presenters.IGridPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridFragment extends BaseFragment implements IGridPresenter.GridView {

    @BindView(R.id.grid_rv) RecyclerView gridRv;

    @BindView(R.id.grid_toolbar) Toolbar gridToolbar;

    @BindView(R.id.grid_pb) ProgressBar progressBar;

    @BindView(R.id.grid_swipe) SwipeRefreshLayout refreshLayout;

    @Inject
    IGridPresenter<IGridPresenter.GridView> presenter;

    private GridAdapter gridAdapter;
    private List<SearchItemModel> items;

    private String duration;
    private String category;
    private int currentPage;

    private GridFragmentListener gridFragmentListener;

    public interface GridFragmentListener {
        void onVideoClick(String id);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.grid_fragment, container, false);

        inject();
        ButterKnife.bind(this, fragmentView);
        presenter.setView(this);

        parseVideosSpecification();

        setupRefreshListener();

        progressBar.setVisibility(View.VISIBLE);
        gridRv.setVisibility(View.GONE);

        instantiateFragment(savedInstanceState);

        return fragmentView;
    }

    private void instantiateFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState
                .getStringArrayList(getString(R.string.category_list_items)) != null) {

            items = savedInstanceState.getParcelableArrayList(getString(R.string.category_list_items));
            populateAdapter(items);
        } else {
            presenter.fetchVideos(category, duration, currentPage);
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
    public void populateAdapter(List<SearchItemModel> newItems) {
        items = newItems;

        progressBar.setVisibility(View.GONE);
        gridRv.setVisibility(View.VISIBLE);

        gridRv.setLayoutManager(buildLayoutManager());

        gridAdapter = new GridAdapter(getContext(), newItems, gridRv);
        gridAdapter.setCurrentPage(currentPage);

        setupOnClickListener();
        setupOnLoadMoreListener();

        gridRv.setAdapter(gridAdapter);

        stopRefreshing();
    }

    private void setupOnClickListener() {
        gridAdapter.setClickListener((itemView, position) -> {
            gridFragmentListener.onVideoClick(items.get(position).getId());
        });
    }

    private void setupOnLoadMoreListener() {
        gridAdapter.setOnLoadMoreListener(page -> {
            items.add(null);
            gridAdapter.notifyItemInserted(items.size() - 1);

            currentPage = page;
            presenter.fetchVideos(category, duration, page);
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
            presenter.fetchVideos(category, duration, currentPage);
        });
    }

    @Override
    public void updateAdapter(List<SearchItemModel> newItems) {
        items.remove(items.size() - 1);
        gridAdapter.notifyItemRemoved(items.size());

        items.addAll(newItems);
        gridAdapter.notifyItemRangeInserted(gridAdapter.getItemCount(), newItems.size());

        gridAdapter.setLoaded();
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

        if (items != null) {
            outState.putParcelableArrayList(getString(R.string.category_list_items), new ArrayList<>(items));
        }
    }
}
