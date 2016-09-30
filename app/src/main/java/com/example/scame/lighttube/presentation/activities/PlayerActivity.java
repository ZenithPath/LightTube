package com.example.scame.lighttube.presentation.activities;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.R;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class PlayerActivity extends YouTubeFailureRecoveryActivity implements
        CompoundButton.OnCheckedChangeListener,
        YouTubePlayer.OnFullscreenListener,
        AppCompatCallback {

    private static final int PORTRAIT_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

    @BindView(R.id.player) YouTubePlayerView playerView;

    @BindView(R.id.fullscreen_button) Button fullscreenButton;

    @BindView(R.id.landscape_fullscreen_checkbox) CompoundButton fullscreenCheckBox;

    @BindView(R.id.other_views) View otherViews;

    @BindView(R.id.player_base_layout) LinearLayout baseLayout;

    @BindView(R.id.player_toolbar) Toolbar toolbar;

    private YouTubePlayer youTubePlayer;

    private boolean fullscreen;

    private AppCompatDelegate delegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        delegate = AppCompatDelegate.create(this, this);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.player_activity);

        ButterKnife.bind(this);

        delegate.setSupportActionBar(toolbar);
        fullscreenCheckBox.setOnCheckedChangeListener(this);
        fullscreenButton.setOnClickListener(view -> youTubePlayer.setFullscreen(!fullscreen));

        playerView.initialize(PrivateValues.API_KEY, this);

        doLayout();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int controlFlags = youTubePlayer.getFullscreenControlFlags();

        if (isChecked) {
            setRequestedOrientation(PORTRAIT_ORIENTATION);
            controlFlags |= YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            controlFlags &= ~YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
        }

        youTubePlayer.setFullscreenControlFlags(controlFlags);
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return playerView;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        this.youTubePlayer = youTubePlayer;

        setControlsEnabled();
        // Specify that we want to handle fullscreen behavior ourselves.
        this.youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
        this.youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        this.youTubePlayer.setOnFullscreenListener(this);

        if (!wasRestored) {
            this.youTubePlayer.cueVideo(getIntent().getStringExtra(getString(R.string.video_id)));
        }
    }

    private void doLayout() {
        LinearLayout.LayoutParams playerParams = (LinearLayout.LayoutParams) playerView.getLayoutParams();

        if (fullscreen) {
            hideAllViews();
            playerParams.width = MATCH_PARENT;
            playerParams.height = MATCH_PARENT;
        } else {
            showAllViews();
            handleOrientation(playerParams);
            setControlsEnabled();
        }
    }

    private void handleOrientation(LinearLayout.LayoutParams playerParams) {
        ViewGroup.LayoutParams otherViewsParams = otherViews.getLayoutParams();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            playerParams.width = otherViewsParams.width = 0;
            playerParams.height = WRAP_CONTENT;
            otherViewsParams.height = MATCH_PARENT;
            playerParams.weight = 1;

            baseLayout.setOrientation(LinearLayout.HORIZONTAL);
        } else {
            playerParams.width = otherViewsParams.width = MATCH_PARENT;
            playerParams.height = WRAP_CONTENT;
            playerParams.weight = 0;
            otherViewsParams.height = 0;

            baseLayout.setOrientation(LinearLayout.VERTICAL);
        }
    }

    private void hideAllViews() {

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        delegate.getSupportActionBar().hide();
        otherViews.setVisibility(View.GONE);
    }

    private void showAllViews() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);

        delegate.getSupportActionBar().show();
        otherViews.setVisibility(View.VISIBLE);
    }

    private void setControlsEnabled() {
        fullscreenCheckBox.setEnabled(youTubePlayer != null
                && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
        fullscreenButton.setEnabled(youTubePlayer != null);
    }

    @Override
    public void onFullscreen(boolean isFullscreen) {
        fullscreen = isFullscreen;
        doLayout();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        doLayout();
    }


    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}
