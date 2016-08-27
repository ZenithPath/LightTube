package com.example.scame.lighttube.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.TabActivity;
import com.example.scame.lighttube.presentation.adapters.GridAdapter;
import com.example.scame.lighttube.presentation.model.SearchItemModel;
import com.example.scame.lighttube.presentation.presenters.IGridPresenter;

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

    private String duration;
    private String category;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.grid_fragment, container, false);

        inject();
        ButterKnife.bind(this, fragmentView);
        presenter.setView(this);

        parseIntent();
        presenter.fetchVideos(category, duration);

        return fragmentView;
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridAdapter = new GridAdapter(getContext(), items);
        gridAdapter.setClickListener((itemView, position) -> {
            // TODO: open a video
        });

        gridRv.setLayoutManager(gridLayoutManager);
        gridRv.setHasFixedSize(true);
        gridRv.setAdapter(gridAdapter);
    }
}
