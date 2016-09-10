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
import com.example.scame.lighttube.presentation.activities.TabActivity;
import com.example.scame.lighttube.presentation.adapters.VideoListAdapter;
import com.example.scame.lighttube.presentation.model.VideoItemModel;
import com.example.scame.lighttube.presentation.presenters.IVideoListPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListFragment extends BaseFragment implements IVideoListPresenter.VideoListView {

    @BindView(R.id.videolist_rv) RecyclerView recyclerView;

    @BindView(R.id.videolist_toolbar) Toolbar toolbar;

    @BindView(R.id.video_list_pb) ProgressBar progressBar;

    @BindView(R.id.video_list_swipe) SwipeRefreshLayout refreshLayout;

    @Inject
    IVideoListPresenter<IVideoListPresenter.VideoListView> presenter;

    private List<VideoItemModel> items;
    private int currentPage;

    private VideoListAdapter adapter;

    private VideoListActivityListener listActivityListener;

    public interface VideoListActivityListener {
        void onVideoClick(String id);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.video_list_fragment, container, false);

        ButterKnife.bind(this, fragmentView);

        setupRefreshListener();

        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        instantiateFragment(savedInstanceState);

        return fragmentView;
    }

    private void instantiateFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState
                .getParcelableArrayList(getString(R.string.video_items_list)) != null) {

            currentPage = savedInstanceState.getInt(getString(R.string.page_number));
            initializeAdapter(savedInstanceState.getParcelableArrayList(getString(R.string.video_items_list)));
        } else {
            presenter.fetchVideos(currentPage);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (items != null ) {
            outState.putParcelableArrayList(getString(R.string.video_items_list), new ArrayList<>(items));
            outState.putInt(getString(R.string.page_number), currentPage);
        }
    }


    @Override
    public void initializeAdapter(List<VideoItemModel> newItems) {
        items = newItems;

        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        recyclerView.setLayoutManager(buildLayoutManager());
        adapter = new VideoListAdapter(newItems, getContext(), recyclerView);
        adapter.setPage(currentPage);

        setupClickListener();
        setupOnLoadMoreListener();

        recyclerView.setAdapter(adapter);

        stopRefreshing();
    }

    private void setupClickListener() {
        adapter.setupOnItemClickListener((itemView, position) ->
                listActivityListener.onVideoClick(items.get(position).getId()));
    }

    private void setupOnLoadMoreListener() {
        adapter.setOnLoadMoreListener((page) -> {
            items.add(null);
            adapter.notifyItemInserted(items.size() - 1);

            currentPage = page;
            presenter.fetchVideos(page);
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
            adapter.setPage(currentPage);
            presenter.fetchVideos(currentPage);
        });
    }

    @Override
    public void updateAdapter(List<VideoItemModel> newItems) {
        items.remove(items.size() - 1);
        adapter.notifyItemRemoved(items.size());

        items.addAll(newItems);
        adapter.notifyItemRangeInserted(adapter.getItemCount(), newItems.size());

        adapter.setLoaded();
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
}
