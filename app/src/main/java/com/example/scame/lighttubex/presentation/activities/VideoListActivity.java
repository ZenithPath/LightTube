package com.example.scame.lighttubex.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.presentation.LightTubeApp;
import com.example.scame.lighttubex.presentation.di.HasComponent;
import com.example.scame.lighttubex.presentation.di.components.ApplicationComponent;
import com.example.scame.lighttubex.presentation.di.components.DaggerVideoListComponent;
import com.example.scame.lighttubex.presentation.di.components.VideoListComponent;
import com.example.scame.lighttubex.presentation.di.modules.VideoListModule;
import com.example.scame.lighttubex.presentation.fragments.VideoListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListActivity extends BaseActivity implements HasComponent<VideoListComponent>,
                                                               VideoListFragment.VideoListActivityListener {

    public static final String VIDEO_LIST_FRAG_TAG = "videoListFragment";

    private VideoListComponent videoListComponent;

    @BindView(R.id.videolist_toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list_activity);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        replaceFragment(R.id.videolist_activity_fl, new VideoListFragment(), VIDEO_LIST_FRAG_TAG);
    }

    @Override
    protected void inject(ApplicationComponent appComponent) {
        videoListComponent = DaggerVideoListComponent
                .builder()
                .applicationComponent(LightTubeApp.getAppComponent())
                .videoListModule(new VideoListModule(this))
                .build();

        videoListComponent.inject(this);
    }

    @Override
    public VideoListComponent getComponent() {
        return videoListComponent;
    }

    @Override
    public void onVideoClick(String videoId) {
        navigator.navigateToPlayVideo(this, videoId);
    }
}
