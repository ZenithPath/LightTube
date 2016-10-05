package com.example.scame.lighttube.presentation.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.adapters.player.CommentsAdapter;
import com.example.scame.lighttube.presentation.model.CommentListModel;
import com.example.scame.lighttube.presentation.presenters.IPlayerPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerFooterFragment extends Fragment implements IPlayerPresenter.PlayerView {

    private static final String LIKE = "like";
    private static final String DISLIKE = "dislike";
    private static final String NONE = "none";

    @Inject
    IPlayerPresenter<IPlayerPresenter.PlayerView> presenter;

    @BindView(R.id.player_footer_rv) RecyclerView recyclerView;

    private String videoId;

    @FunctionalInterface
    public interface LikeListener {

        void onLikeClick(boolean ratedPositively);
    }

    @FunctionalInterface
    public interface DislikeListener {

        void onDislikeClick(boolean ratedNegatively);
    }

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
        presenter.getCommentList(videoId);

        return fragmentView;
    }


    @Override
    public void displayComments(CommentListModel commentListModel) {
        CommentsAdapter commentsAdapter = new CommentsAdapter(commentListModel.getThreadComments(),
                getActivity(), this::onLikeClick, this::onDislikeClick, "Some title");

        recyclerView.setAdapter(commentsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void onLikeClick(boolean ratedPositively) {
        if (!ratedPositively) {
            presenter.rateVideo(videoId, NONE);
        } else {
            presenter.rateVideo(videoId, LIKE);
        }
    }

    public void onDislikeClick(boolean ratedNegatively) {
        if (!ratedNegatively) {
            presenter.rateVideo(videoId, NONE);
        } else {
            presenter.rateVideo(videoId, DISLIKE);
        }
    }

    @Override
    public void displayRating(String rating) {
        if (rating.equals(LIKE)) {
       //     likeBtn.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        } else if (rating.equals(DISLIKE)) {
       //     dislikeBtn.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.destroy();
    }
}
