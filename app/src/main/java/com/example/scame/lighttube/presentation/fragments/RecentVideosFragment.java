package com.example.scame.lighttube.presentation.fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.TabActivity;
import com.example.scame.lighttube.presentation.adapters.ChannelsAdapter;
import com.example.scame.lighttube.presentation.adapters.RecentVideosAdapter;
import com.example.scame.lighttube.presentation.model.SearchItemModel;
import com.example.scame.lighttube.presentation.presenters.IRecentVideosPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentVideosFragment extends BaseFragment implements IRecentVideosPresenter.RecentVideosView,
                                                            ChannelsAdapter.OnItemClickListener,
                                                            RecentVideosAdapter.OnItemClickListener {

    @BindView(R.id.channels_rv) RecyclerView channelsRv;
    @BindView(R.id.recent_rv) RecyclerView recentVideosRv;

    @BindView(R.id.recent_app_bar) AppBarLayout appBarLayout;
    @BindView(R.id.recent_toolbar) Toolbar toolbar;

    @Inject
    IRecentVideosPresenter<IRecentVideosPresenter.RecentVideosView> presenter;

    private List<SearchItemModel> listItems;
    private RecentVideosAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() instanceof TabActivity) {
            TabActivity tabActivity = (TabActivity) getActivity();
            tabActivity.getRecentVideosComponent().inject(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.recent_videos_fragment, container, false);

        ButterKnife.bind(this, fragmentView);
        presenter.setView(this);
        appBarLayout.setExpanded(false);

        if (savedInstanceState != null && savedInstanceState
                .getStringArrayList(getString(R.string.video_items_list)) != null) {

            listItems = savedInstanceState.getParcelableArrayList(getString(R.string.video_items_list));
            populateAdapter(listItems);
        } else {
            presenter.fetchRecentVideos();
        }

        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void populateAdapter(List<SearchItemModel> items) {
        listItems = items;

        adapter = new RecentVideosAdapter(items, getContext());
        adapter.setupOnItemClickListener(this);
        recentVideosRv.setAdapter(adapter);
        recentVideosRv.setHasFixedSize(true);
        recentVideosRv.setLayoutManager(buildLayoutManager());

        appBarLayout.setExpanded(true, true);
    }

    @Override
    public void updateAdapter(List<SearchItemModel> items) {

    }

    private LinearLayoutManager buildLayoutManager() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        } else {
            return new LinearLayoutManager(getContext());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (listItems != null) {
            outState.putParcelableArrayList(getString(R.string.video_items_list), new ArrayList<>(listItems));
        }
    }

    @Override
    public void onChannelClick(View itemView, int position) {
        // TODO: implement channel click logic
    }

    @Override
    public void onItemClick(View itemView, int position) {
        // TODO: implement video click logic
    }
}
