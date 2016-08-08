package com.example.scame.lighttubex.presentation.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.presentation.adapters.EndlessRecyclerViewScrollingListener;
import com.example.scame.lighttubex.presentation.adapters.VideoListAdapter;
import com.example.scame.lighttubex.presentation.di.components.VideoListComponent;
import com.example.scame.lighttubex.presentation.model.VideoItemModel;
import com.example.scame.lighttubex.presentation.presenters.IVideoListPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListFragment extends BaseFragment implements IVideoListPresenter.VideoListView {

    @BindView(R.id.videolist_rv) RecyclerView recyclerView;

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

        getComponent(VideoListComponent.class).inject(this);
        presenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.video_list_fragment, container, false);

        ButterKnife.bind(this, fragmentView);

        List<VideoItemModel> savedItems = null;
        if (savedInstanceState != null) {
            savedItems = savedInstanceState
                    .getParcelableArrayList(getString(R.string.video_items_list));
            currentPage = savedInstanceState
                    .getInt(getString(R.string.page_number));
        }

        presenter.fetchVideos(currentPage, savedItems);

        return fragmentView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(getString(R.string.video_items_list), new ArrayList<>(items));
        outState.putInt(getString(R.string.page_number), currentPage);
    }


    @Override
    public void initializeAdapter(List<VideoItemModel> items) {
        this.items = items;
        this.adapter = new VideoListAdapter(items, getContext());
        adapter.setupOnItemClickListener((itemView, position) ->
                listActivityListener.onVideoClick(items.get(position).getId()));

        LinearLayoutManager layoutManager = buildLayoutManager();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(buildScrollingListener(layoutManager));
    }

    @Override
    public void updateAdapter(List<VideoItemModel> items) {
        this.items.addAll(items);
        adapter.notifyItemRangeInserted(adapter.getItemCount(), items.size());
    }

    private EndlessRecyclerViewScrollingListener buildScrollingListener(LinearLayoutManager manager) {
        EndlessRecyclerViewScrollingListener listener = new EndlessRecyclerViewScrollingListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                currentPage = page;
                presenter.fetchVideos(page, null);
            }
        };

        listener.setCurrentPage(currentPage);

        return listener;
    }

    private LinearLayoutManager buildLayoutManager() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        } else {
            return new LinearLayoutManager(getContext());
        }
    }
}
