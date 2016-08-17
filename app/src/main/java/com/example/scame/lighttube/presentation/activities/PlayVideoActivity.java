package com.example.scame.lighttube.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.di.components.ApplicationComponent;
import com.example.scame.lighttube.presentation.fragments.PlayVideoFragment;

public class PlayVideoActivity extends BaseActivity {

    public static final String PLAY_VIDEO_FRAG_TAG = "playVideo";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_video_activity);

        setupFragment();
    }

    @Override
    protected void inject(ApplicationComponent appComponent) {

    }

    void setupFragment() {
        Bundle args = new Bundle();
        args.putString(getString(R.string.video_id), getIntent().getStringExtra(getString(R.string.video_id)));

        PlayVideoFragment fragment = new PlayVideoFragment();
        fragment.setArguments(args);
        replaceFragment(R.id.play_video_fl, fragment, PLAY_VIDEO_FRAG_TAG);
    }
}
