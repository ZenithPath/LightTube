package com.example.scame.lighttube.presentation.fragments;

import android.content.Context;
import android.content.res.Configuration;
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
import com.example.scame.lighttube.data.repository.PaginationUtility;
import com.example.scame.lighttube.presentation.activities.TabActivity;
import com.example.scame.lighttube.presentation.adapters.BaseAdapter;
import com.example.scame.lighttube.presentation.adapters.HomeVideosAdapter;
import com.example.scame.lighttube.presentation.model.VideoModel;
import com.example.scame.lighttube.presentation.presenters.HomePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.State;

public class HomeFragment extends BaseFragment implements HomePresenter.VideoListView, ScrollingHelperListener {

    @BindView(R.id.videolist_rv) RecyclerView recyclerView;

    @BindView(R.id.videolist_toolbar) Toolbar toolbar;

    @BindView(R.id.video_list_pb) ProgressBar progressBar;

    @BindView(R.id.video_list_swipe) SwipeRefreshLayout refreshLayout;

    @Inject
    HomePresenter<HomePresenter.VideoListView> presenter;

    @Inject
    @Named("general")
    PaginationUtility paginationUtility;

    @State ArrayList<Object> items;

    private BaseAdapter adapter;

    private VideoListActivityListener listActivityListener;

    private RecyclerScrollingHelper scrollingHelper;

    private Bundle savedInstanceState;

    public interface VideoListActivityListener {

        void onVideoClick(VideoModel videoModel);

        void onScrolled(boolean scrolledToTop);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof VideoListActivityListener) {
            listActivityListener = (VideoListActivityListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inject();
        presenter.setView(this);
    }

    private void inject() {
        if (getActivity() instanceof TabActivity) {
            TabActivity tabActivity = (TabActivity) getActivity();
            tabActivity.getVideoListComponent().inject(this);
        }
    }

    @Override
    public void onPageChange(int pageNumber) {
        presenter.fetchVideos(pageNumber);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.video_list_fragment, container, false);

        ButterKnife.bind(this, fragmentView);

        this.savedInstanceState = savedInstanceState;
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        instantiateFragment(savedInstanceState);

        return fragmentView;
    }

    private void instantiateFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null && items != null) {
            initializeAdapter(items);
        } else {
            presenter.fetchVideos(0);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (scrollingHelper != null) {
            scrollingHelper.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void initializeAdapter(List<?> newItems) {
        items = new ArrayList<>(newItems);

        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        recyclerView.setLayoutManager(buildLayoutManager());
        adapter = new HomeVideosAdapter(items, getContext(), recyclerView);

        initializeScrollingHelper();
        setupListeners();

        recyclerView.setAdapter(adapter);
        refreshLayout.setRefreshing(false);
    }

    private void initializeScrollingHelper() {
        scrollingHelper = new RecyclerScrollingHelper(items, adapter, refreshLayout, this);
        scrollingHelper.setPaginationUtility(paginationUtility);

        if (savedInstanceState != null) {
            scrollingHelper.onRestoreInstanceState(savedInstanceState);
        }
    }

    private void setupListeners() {
        scrollingHelper.setupRetryListener();
        scrollingHelper.setupOnLoadMoreListener();
        scrollingHelper.setupNoConnectionListener();
        scrollingHelper.setupRefreshListener();

        setupOnVideoClickListener();
        setupDirectionScrollListener();
    }

    private void setupDirectionScrollListener() {
        adapter.setDirectionScrollListener(scrollToTop -> listActivityListener.onScrolled(scrollToTop));
    }

    private void setupOnVideoClickListener() {
        if (adapter instanceof HomeVideosAdapter) {
            HomeVideosAdapter videoAdapter = (HomeVideosAdapter) adapter;
            videoAdapter.setupOnItemClickListener((itemView, position) -> {
                VideoModel videoModel = ((VideoModel) items.get(position));
                listActivityListener.onVideoClick(videoModel);
            });
        }
    }

    @Override
    public void updateAdapter(List<?> newItems) {
        items.remove(items.size() - 1);
        adapter.notifyItemRemoved(items.size());

        items.addAll(newItems);
        adapter.notifyItemRangeInserted(adapter.getItemCount(), newItems.size());

        adapter.setLoading(false);
    }

    private LinearLayoutManager buildLayoutManager() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        } else {
            return new LinearLayoutManager(getContext());
        }
    }

    public void scrollToTop() {
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.destroy();
    }
}
