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
import com.example.scame.lighttube.presentation.activities.TabActivity;
import com.example.scame.lighttube.presentation.adapters.ChannelVideosAdapter;
import com.example.scame.lighttube.presentation.model.SearchItemModel;
import com.example.scame.lighttube.presentation.presenters.IChannelsPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChannelVideosFragment extends BaseFragment implements IChannelsPresenter.ChannelsView {

    @Inject
    IChannelsPresenter<IChannelsPresenter.ChannelsView> presenter;

    @BindView(R.id.channels_fragment_rv) RecyclerView recyclerView;

    @BindView(R.id.channels_pb) ProgressBar progressBar;

    @BindView(R.id.channels_toolbar) Toolbar toolbar;

    @BindView(R.id.channels_swipe) SwipeRefreshLayout refreshLayout;

    private ChannelVideosAdapter channelAdapter;
    private List<SearchItemModel> searchItems;

    private ChannelVideosListener channelVideosListener;

    private String channelId;

    private int currentPage;

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
        if (savedInstanceState != null &&
                savedInstanceState.getParcelableArrayList(getString(R.string.channel_models_key)) != null) {

            searchItems = savedInstanceState.getParcelableArrayList(getString(R.string.channel_models_key));
            currentPage = savedInstanceState.getInt(getString(R.string.page_number));

            populateAdapter(searchItems);
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
    public void populateAdapter(List<SearchItemModel> searchItemModels) {
        this.searchItems = searchItemModels;

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        channelAdapter = new ChannelVideosAdapter(searchItemModels, getContext(), recyclerView);
        channelAdapter.setPage(currentPage);

        setupAdapterOnItemClickListener();
        setupAdapterOnLoadMoreListener();

        recyclerView.setAdapter(channelAdapter);

        stopRefreshing();
    }

    private void setupAdapterOnItemClickListener() {
        channelAdapter.setupOnItemClickListener((itemView, position) ->
                channelVideosListener.onVideoClick(searchItems.get(position).getId()));
    }

    private void setupAdapterOnLoadMoreListener() {
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
            presenter.fetchChannelVideos(channelId, currentPage);
        });
    }

    private void stopRefreshing() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void updateAdapter(List<SearchItemModel> newItems) {
        searchItems.remove(searchItems.size() - 1);
        channelAdapter.notifyItemRemoved(searchItems.size());

        searchItems.addAll(newItems);
        channelAdapter.notifyItemRangeInserted(channelAdapter.getItemCount(), newItems.size());

        channelAdapter.setLoaded();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(getString(R.string.channel_models_key), new ArrayList<>(searchItems));
        outState.putInt(getString(R.string.page_number), currentPage);
    }
}
