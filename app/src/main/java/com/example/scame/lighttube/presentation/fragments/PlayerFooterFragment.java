package com.example.scame.lighttube.presentation.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.presenters.IPlayerPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerFooterFragment extends Fragment implements IPlayerPresenter.PlayerView {

    private static final String LIKE = "like";
    private static final String DISLIKE = "dislike";
    private static final String NONE = "none";

    @Inject
    IPlayerPresenter<IPlayerPresenter.PlayerView> presenter;

    @BindView(R.id.video_title_player) TextView videoTitle;

    @BindView(R.id.like_btn) ImageButton likeBtn;

    @BindView(R.id.dislike_btn) ImageButton dislikeBtn;

    private String videoId;

    public static PlayerFooterFragment newInstance(String videoId) {
        PlayerFooterFragment fragment = new PlayerFooterFragment();

        Bundle args = new Bundle();
        args.putString(PlayerFooterFragment.class.getCanonicalName(), videoId);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.player_footer_fragment, container, false);

        videoId = getArguments().getString(PlayerFooterFragment.class.getCanonicalName());
        ((PlayerActivity) getActivity()).getPlayerComponent().inject(this);
        ButterKnife.bind(this, fragmentView);

        presenter.setView(this);
        presenter.getVideoRating(videoId);

        return fragmentView;
    }

    @SuppressLint("NewApi")
    @OnClick(R.id.like_btn)
    public void onLikeClick() {
        if (likeBtn.getColorFilter() != null) {
            likeBtn.setColorFilter(null);
            presenter.rateVideo(videoId, NONE);
        } else {
            likeBtn.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            dislikeBtn.setColorFilter(null);
            presenter.rateVideo(videoId, LIKE);
        }
    }

    @SuppressLint("NewApi")
    @OnClick(R.id.dislike_btn)
    public void onDislikeClick() {
        if (dislikeBtn.getColorFilter() != null) {
            dislikeBtn.setColorFilter(null);
            presenter.rateVideo(videoId, NONE);
        } else {
            dislikeBtn.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            likeBtn.setColorFilter(null);
            presenter.rateVideo(videoId, DISLIKE);
        }
    }

    @Override
    public void displayRating(String rating) {
        if (rating.equals(LIKE)) {
            likeBtn.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        } else if (rating.equals(DISLIKE)) {
            dislikeBtn.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        }
    }
}
