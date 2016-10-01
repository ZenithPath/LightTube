package com.example.scame.lighttube.presentation.activities;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.R;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class PlayerActivity extends YouTubeFailureRecoveryActivity implements
        YouTubePlayer.OnFullscreenListener,
        AppCompatCallback {

    private static final int PORTRAIT_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

    @BindView(R.id.player) YouTubePlayerView playerView;

    @BindView(R.id.player_toolbar) Toolbar toolbar;

    @BindView(R.id.video_title_player) TextView videoTitle;

    @BindView(R.id.like_btn) ImageButton likeBtn;

    @BindView(R.id.dislike_btn) ImageButton dislikeBtn;

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

        playerView.initialize(PrivateValues.API_KEY, this);

        doLayout();
    }

    @SuppressLint("NewApi")
    @OnClick(R.id.like_btn)
    public void onLikeClick() {
        if (likeBtn.getColorFilter() != null) {
            likeBtn.setColorFilter(null);
        } else {
            likeBtn.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            dislikeBtn.setColorFilter(null);
        }
    }

    @SuppressLint("NewApi")
    @OnClick(R.id.dislike_btn)
    public void onDislikeClick() {
        if (dislikeBtn.getColorFilter() != null) {
            dislikeBtn.setColorFilter(null);
        } else {
            dislikeBtn.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            likeBtn.setColorFilter(null);
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return playerView;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        this.youTubePlayer = youTubePlayer;

        // specify that we want to handle fullscreen behavior ourselves
        this.youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
        this.youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        this.youTubePlayer.setOnFullscreenListener(this);

        setPortraitFullscreen();

        if (!wasRestored) {
            this.youTubePlayer.cueVideo(getIntent().getStringExtra(getString(R.string.video_id)));
        }
    }

    private void setPortraitFullscreen() {
        int controlFlags = youTubePlayer.getFullscreenControlFlags();
        setRequestedOrientation(PORTRAIT_ORIENTATION);
        controlFlags |= YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
        youTubePlayer.setFullscreenControlFlags(controlFlags);
    }

    private void doLayout() {
        LinearLayout.LayoutParams playerParams = (LinearLayout.LayoutParams) playerView.getLayoutParams();

        if (fullscreen) {
            hideAllViews();
            playerParams.width = MATCH_PARENT;
            playerParams.height = MATCH_PARENT;
        } else {
            showAllViews();
        }
    }

    private void hideAllViews() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        if (delegate.getSupportActionBar() != null) {
            delegate.getSupportActionBar().hide();
        }
    }

    private void showAllViews() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);

        if (delegate.getSupportActionBar() != null) {
            delegate.getSupportActionBar().show();
        }
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
