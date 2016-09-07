package com.example.scame.lighttube.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.scame.lighttube.presentation.adapters.ChannelsVideosAdapter;
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

    private ChannelsVideosAdapter fragmentAdapter;
    private List<SearchItemModel> searchItemModels;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.channels_fragment, container, false);

        inject();
        ButterKnife.bind(this, fragmentView);
        presenter.setView(this);

        parseIntent();

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        if (savedInstanceState != null &&
                savedInstanceState.getParcelableArrayList(getString(R.string.channel_models_key)) != null) {

            searchItemModels = savedInstanceState.getParcelableArrayList(getString(R.string.channel_models_key));
            populateAdapter(searchItemModels);
        } else {
            presenter.fetchChannelVideos(channelId);
        }

        return fragmentView;
    }

    void parseIntent() {
        Bundle args = getArguments();
        channelId = args.getString(ChannelVideosFragment.class.getCanonicalName());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void inject() {
        if (getActivity() instanceof TabActivity) {
            TabActivity tabActivity = (TabActivity) getActivity();
            tabActivity.getChannelVideosComponent().inject(this);
        }
    }

    @Override
    public void populateAdapter(List<SearchItemModel> searchItemModels) {
        this.searchItemModels = searchItemModels;

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        fragmentAdapter = new ChannelsVideosAdapter(searchItemModels, getContext());
        fragmentAdapter.setupOnItemClickListener((itemView, position) ->
                channelVideosListener.onVideoClick(searchItemModels.get(position).getId()));

        recyclerView.setAdapter(fragmentAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(getString(R.string.channel_models_key), new ArrayList<>(searchItemModels));
    }
}
