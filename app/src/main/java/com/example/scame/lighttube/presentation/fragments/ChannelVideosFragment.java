package com.example.scame.lighttube.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.scame.lighttube.presentation.ConnectivityReceiver;
import com.example.scame.lighttube.presentation.activities.TabActivity;
import com.example.scame.lighttube.presentation.adapters.BaseAdapter;
import com.example.scame.lighttube.presentation.adapters.ChannelVideosAdapter;
import com.example.scame.lighttube.presentation.adapters.NoConnectionMarker;
import com.example.scame.lighttube.presentation.model.ModelMarker;
import com.example.scame.lighttube.presentation.model.SearchItemModel;
import com.example.scame.lighttube.presentation.presenters.IChannelVideosPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.State;

public class ChannelVideosFragment extends BaseFragment implements IChannelVideosPresenter.ChannelsView {

    @Inject
    IChannelVideosPresenter<IChannelVideosPresenter.ChannelsView> presenter;

    @BindView(R.id.channels_fragment_rv) RecyclerView recyclerView;

    @BindView(R.id.channels_pb) ProgressBar progressBar;

    @BindView(R.id.channels_toolbar) Toolbar toolbar;

    @BindView(R.id.channels_swipe) SwipeRefreshLayout refreshLayout;

    @State ArrayList<ModelMarker> searchItems;

    // these variables represent adapter state
    @State int currentPage;
    @State boolean isLoading;
    @State boolean isConnectedPreviously = true;

    private BaseAdapter channelAdapter;

    private ChannelVideosListener channelVideosListener;

    private String channelId;

    public interface ChannelVideosListener {

        void onVideoClick(String videoId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof ChannelVideosListener) {
            channelVideosListener = (ChannelVideosListener) getActivity();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.channels_fragment, container, false);

        inject();
        ButterKnife.bind(this, fragmentView);
        presenter.setView(this);

        parseChannelId();

        setupRefreshLayoutListener();

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        instantiateFragment(savedInstanceState);

        return fragmentView;
    }

    private void instantiateFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null && searchItems != null) {
            initializeAdapter(searchItems);
        } else {
            presenter.fetchChannelVideos(channelId, currentPage);
        }
    }

    void parseChannelId() {
        Bundle args = getArguments();
        channelId = args.getString(ChannelVideosFragment.class.getCanonicalName());
    }

    private void inject() {
        if (getActivity() instanceof TabActivity) {
            TabActivity tabActivity = (TabActivity) getActivity();
            tabActivity.getChannelVideosComponent().inject(this);
        }
    }

    @Override
    public void initializeAdapter(List<? extends ModelMarker> newItems) {
        searchItems = new ArrayList<>(newItems);

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        channelAdapter = new ChannelVideosAdapter(searchItems, getContext(), recyclerView);
        channelAdapter.setCurrentPage(currentPage);
        channelAdapter.setConnectedPreviously(isConnectedPreviously);
        channelAdapter.setLoading(isLoading);

        setupRetryListener();
        setupOnVideoClickListener();
        setupOnLoadMoreListener();
        setupNoConnectionListener();

        recyclerView.setAdapter(channelAdapter);

        stopRefreshing();
    }

    @Override
    public void updateAdapter(List<? extends ModelMarker> newItems) {
        searchItems.remove(searchItems.size() - 1);
        channelAdapter.notifyItemRemoved(searchItems.size());

        searchItems.addAll(newItems);
        channelAdapter.notifyItemRangeInserted(channelAdapter.getItemCount(), newItems.size());

        channelAdapter.setLoading(false);
    }

    private void setupRetryListener() {
        channelAdapter.setOnRetryClickListener(() -> {

            if (ConnectivityReceiver.isConnected()) {
                stopRefreshing();

                searchItems.remove(searchItems.size() - 1);
                channelAdapter.notifyItemRemoved(searchItems.size());

                searchItems.add(null);
                channelAdapter.notifyItemInserted(searchItems.size() - 1);

                ++currentPage;
                channelAdapter.setLoading(true);
                channelAdapter.setConnectedPreviously(true);
                channelAdapter.setCurrentPage(currentPage);
                presenter.fetchChannelVideos(channelId, currentPage);
            }
        });
    }

    private void setupOnVideoClickListener() {
        if (channelAdapter instanceof ChannelVideosAdapter) {
            ChannelVideosAdapter videosAdapter = (ChannelVideosAdapter) channelAdapter;
            videosAdapter.setupOnItemClickListener((itemView, position) -> {
                String videoId = ((SearchItemModel) searchItems.get(position)).getId();
                channelVideosListener.onVideoClick(videoId);
            });
        }
    }


    private void setupNoConnectionListener() {
        channelAdapter.setNoConnectionListener(() -> {
            searchItems.add(new NoConnectionMarker());
            channelAdapter.notifyItemInserted(searchItems.size() - 1);
        });
    }

    private void setupOnLoadMoreListener() {
        channelAdapter.setOnLoadMoreListener(page -> {
            searchItems.add(null);
            channelAdapter.notifyItemInserted(searchItems.size() - 1);

            currentPage = page;
            presenter.fetchChannelVideos(channelId, currentPage);
        });
    }

    private void setupRefreshLayoutListener() {
        refreshLayout.setOnRefreshListener(() -> {

            currentPage = 0;
            isLoading = false;
            isConnectedPreviously = true;

            presenter.fetchChannelVideos(channelId, currentPage);
        });
    }

    private void stopRefreshing() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        isLoading = channelAdapter.isLoading();
        isConnectedPreviously = channelAdapter.isConnectedPreviously();

        super.onSaveInstanceState(outState);
    }
}
