package com.example.scame.lighttubex.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.presentation.adapters.EndlessRecyclerViewScrollingListener;
import com.example.scame.lighttubex.presentation.adapters.VideoListAdapter;
import com.example.scame.lighttubex.presentation.di.components.VideoListComponent;
import com.example.scame.lighttubex.presentation.model.VideoItemModel;
import com.example.scame.lighttubex.presentation.presenters.IVideoListPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListFragment extends BaseFragment implements IVideoListPresenter.VideoListView {

    @BindView(R.id.videolist_rv) RecyclerView recyclerView;

    @Inject
    IVideoListPresenter<IVideoListPresenter.VideoListView> presenter;

    private List<VideoItemModel> items;

    private VideoListAdapter adapter;

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

        presenter.fetchVideos(0);

        return fragmentView;
    }

    @Override
    public void populateAdapter(List<VideoItemModel> items) {
        this.items = items;
        this.adapter = new VideoListAdapter(items, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollingListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                presenter.fetchVideos(page);
            }
        });
    }

    @Override
    public void updateAdapter(List<VideoItemModel> items) {
        this.items.addAll(items);
        adapter.notifyItemRangeInserted(adapter.getItemCount(), items.size());
    }
}
