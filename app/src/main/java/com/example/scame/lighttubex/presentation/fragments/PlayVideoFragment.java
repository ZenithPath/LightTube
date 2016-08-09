package com.example.scame.lighttubex.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttubex.PrivateValues;
import com.example.scame.lighttubex.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class PlayVideoFragment extends BaseFragment {

    public static final String PLAY_VIDEO_NESTED_TAG = "playVideoNested";

    private YouTubePlayer player;

    private String videoId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.play_video_fragment, container, false);

        videoId = getArguments().getString(getString(R.string.video_id));

        YouTubePlayerSupportFragment fragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.player_fl, fragment, PLAY_VIDEO_NESTED_TAG);
        transaction.commit();

        fragment.initialize(PrivateValues.API_KEY, buildOnInitializedListener());

        return fragmentView;
    }

    private YouTubePlayer.OnInitializedListener buildOnInitializedListener() {
        return new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    player = youTubePlayer;
                    player.setFullscreen(true);
                    player.loadVideo(videoId);
                    player.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult initResult) {

            }
        };
    }
}
