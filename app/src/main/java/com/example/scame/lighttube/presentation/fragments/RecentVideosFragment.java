package com.example.scame.lighttube.presentation.fragments;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.TabActivity;
import com.example.scame.lighttube.presentation.adapters.ChannelsViewAdapter;
import com.example.scame.lighttube.presentation.adapters.RecentVideosAdapter;
import com.example.scame.lighttube.presentation.model.ChannelModel;
import com.example.scame.lighttube.presentation.model.SearchItemModel;
import com.example.scame.lighttube.presentation.presenters.IRecentVideosPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentVideosFragment extends BaseFragment implements IRecentVideosPresenter.RecentVideosView {

    @BindView(R.id.channels_rv) RecyclerView channelsRv;
    @BindView(R.id.recent_rv) RecyclerView recentVideosRv;

    @BindView(R.id.recent_app_bar) AppBarLayout appBarLayout;
    @BindView(R.id.recent_toolbar) Toolbar toolbar;

    @BindView(R.id.recent_videos_pb) ProgressBar progressBar;

    @BindView(R.id.recent_swipe) SwipeRefreshLayout refreshLayout;

    @Inject
    IRecentVideosPresenter<IRecentVideosPresenter.RecentVideosView> presenter;

    private RecentVideosListener recentVideosListener;

    private List<SearchItemModel> videoItems;
    private RecentVideosAdapter recentVideosAdapter;

    private List<ChannelModel> channelItems;
    private ChannelsViewAdapter channelsViewAdapter;

    public interface RecentVideosListener {

        void onChannelClick(String channelId);

        void onVideoClick(String videoId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof RecentVideosListener) {
            recentVideosListener = (RecentVideosListener) getActivity();
        }
    }

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

        refreshLayout.setOnRefreshListener(() -> presenter.initialize());

        progressBar.setVisibility(View.VISIBLE);
        recentVideosRv.setVisibility(View.GONE);

        instantiateFragment(savedInstanceState);

        return fragmentView;
    }

    private void instantiateFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState
                .getStringArrayList(getString(R.string.video_items_list)) != null) {

            videoItems = savedInstanceState.getParcelableArrayList(getString(R.string.video_items_list));
            channelItems = savedInstanceState.getParcelableArrayList(getString(R.string.channel_models_key));

            populateAdapter(videoItems);
            visualizeChannelsList(channelItems);

        } else {
            presenter.initialize();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void visualizeChannelsList(List<ChannelModel> channelModels) {
        channelItems = channelModels;

        channelsViewAdapter = new ChannelsViewAdapter(channelModels, getContext());
        channelsViewAdapter.setupOnItemClickListener((itemView, position) ->
                recentVideosListener.onChannelClick(channelItems.get(position).getChannelId()));

        channelsRv.setAdapter(channelsViewAdapter);
        channelsRv.setHasFixedSize(true);
        channelsRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void populateAdapter(List<SearchItemModel> items) {
        videoItems = items;

        progressBar.setVisibility(View.GONE);
        recentVideosRv.setVisibility(View.VISIBLE);

        recentVideosAdapter = new RecentVideosAdapter(items, getContext());
        recentVideosAdapter.setupOnItemClickListener((itemView, position) ->
                recentVideosListener.onVideoClick(videoItems.get(position).getId()));

        recentVideosRv.setAdapter(recentVideosAdapter);
        recentVideosRv.setHasFixedSize(true);
        recentVideosRv.setLayoutManager(buildLayoutManager());

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            appBarLayout.setExpanded(true, true);
        }

        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
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

        if (videoItems != null) {
            outState.putParcelableArrayList(getString(R.string.video_items_list), new ArrayList<>(videoItems));
            outState.putParcelableArrayList(getString(R.string.channel_models_key), new ArrayList<>(channelItems));
        }
    }

    public void scrollToTop() {
        recentVideosRv.smoothScrollToPosition(0);
    }
}
