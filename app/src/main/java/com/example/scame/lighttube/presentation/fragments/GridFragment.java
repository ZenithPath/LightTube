package com.example.scame.lighttube.presentation.fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.TabActivity;
import com.example.scame.lighttube.presentation.adapters.EndlessRecyclerViewScrollingListener;
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

    @Inject
    IGridPresenter<IGridPresenter.GridView> presenter;

    private GridAdapter gridAdapter;
    private List<SearchItemModel> items;

    private String duration;
    private String category;
    private int currentPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.grid_fragment, container, false);

        inject();
        ButterKnife.bind(this, fragmentView);
        presenter.setView(this);

        parseIntent();

        if (savedInstanceState != null && savedInstanceState
                .getStringArrayList(getString(R.string.category_list_items)) != null) {

            restoreState(savedInstanceState);
        } else {
            presenter.fetchVideos(category, duration, currentPage);
        }

        return fragmentView;
    }

    private void restoreState(Bundle savedInstanceState) {
        items = savedInstanceState.getParcelableArrayList(getString(R.string.category_list_items));
        populateAdapter(items);
    }

    private void parseIntent() {
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(gridToolbar);
    }


    @Override
    public void populateAdapter(List<SearchItemModel> items) {
        this.items = items;

        GridLayoutManager gridLayoutManager = buildLayoutManager();
        gridAdapter = new GridAdapter(getContext(), items);
        gridAdapter.setClickListener((itemView, position) -> {
            // TODO: open a video
        });

        gridRv.setLayoutManager(gridLayoutManager);
        gridRv.setHasFixedSize(true);
        gridRv.setAdapter(gridAdapter);
        gridRv.addOnScrollListener(buildScrollingListener(gridLayoutManager));
    }

    @Override
    public void updateAdapter(List<SearchItemModel> items) {
        this.items.addAll(items);
        gridAdapter.notifyItemRangeInserted(gridAdapter.getItemCount(), items.size());
    }

    private GridLayoutManager buildLayoutManager() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new GridLayoutManager(getContext(), 3);
        } else {
            return new GridLayoutManager(getContext(), 2);
        }
    }

    private EndlessRecyclerViewScrollingListener buildScrollingListener(LinearLayoutManager manager) {
        EndlessRecyclerViewScrollingListener listener = new EndlessRecyclerViewScrollingListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                currentPage = page;
                presenter.fetchVideos(category, duration, page);
            }
        };

        listener.setCurrentPage(currentPage);

        return listener;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (items != null) {
            outState.putParcelableArrayList(getString(R.string.category_list_items), new ArrayList<>(items));
        }
    }
}
