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
import android.widget.LinearLayout;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.di.components.CommentsComponent;
import com.example.scame.lighttube.presentation.di.components.ComponentsManager;
import com.example.scame.lighttube.presentation.di.components.PlayerFooterComponent;
import com.example.scame.lighttube.presentation.di.components.RepliesComponent;
import com.example.scame.lighttube.presentation.fragments.PlayerFooterFragment;
import com.example.scame.lighttube.presentation.fragments.RepliesFragment;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class PlayerActivity extends YouTubeFailureRecoveryActivity implements
        YouTubePlayer.OnFullscreenListener,
        AppCompatCallback,
        PlayerFooterFragment.PlayerFooterListener {

    private static final int PORTRAIT_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

    private static final String PLAYER_FOOTER_TAG = "playerFooter";

    private static final String REPLIES_FRAG_TAG = "repliesFragment";

    @BindView(R.id.player) YouTubePlayerView playerView;

    @BindView(R.id.player_toolbar) Toolbar toolbar;

    private YouTubePlayer youTubePlayer;

    private boolean fullscreen;

    private String videoId;

    private AppCompatDelegate delegate;

    private ComponentsManager componentsManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        delegate = AppCompatDelegate.create(this, this);
        delegate.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.player_activity);

        componentsManager = new ComponentsManager(this);
        videoId = getIntent().getStringExtra(getString(R.string.video_id));

        ButterKnife.bind(this);

        delegate.setSupportActionBar(toolbar);

        playerView.initialize(PrivateValues.API_KEY, this);

        doLayout();
        instantiateFooterFragment();
    }


    private void instantiateFooterFragment() {
        if (getFragmentManager().findFragmentByTag(PLAYER_FOOTER_TAG) == null) {
            PlayerFooterFragment fragment = PlayerFooterFragment.newInstance(videoId);
            getFragmentManager().beginTransaction()
                    .replace(R.id.player_activity_fl, fragment, PLAYER_FOOTER_TAG)
                    .commit();
        }
    }

    @Override
    public void onRepliesClick(String threadCommentId) {
        RepliesFragment repliesFragment = RepliesFragment.newInstance(threadCommentId);
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.player_activity_fl, repliesFragment, REPLIES_FRAG_TAG)
                .commit();
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
            this.youTubePlayer.cueVideo(videoId);
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
            playerParams.height = WRAP_CONTENT;
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

    public CommentsComponent getCommentsComponent() {
        return componentsManager.provideCommentsComponent();
    }

    public PlayerFooterComponent getPlayerFooterComponent() {
        return componentsManager.providePlayerFooterComponent();
    }

    public RepliesComponent getRepliesComponent() {
        return componentsManager.provideRepliesComponent();
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
