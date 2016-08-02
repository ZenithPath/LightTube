package com.example.scame.lighttubex.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.presentation.adapters.VideoListAdapter;
import com.example.scame.lighttubex.presentation.model.VideoItemModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListFragment extends BaseFragment {

    @BindView(R.id.videolist_rv) RecyclerView recyclerView;

    private List<VideoItemModel> items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.video_list_fragment, container, false);

        ButterKnife.bind(this, fragmentView);

        // TODO: implement items fetching
        // items = getItems();
        VideoListAdapter adapter = new VideoListAdapter(items, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return fragmentView;
    }
}
