package com.example.scame.lighttube.presentation.adapters.player;


import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.presenters.IPlayerHeaderPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HeaderViewHolder extends RecyclerView.ViewHolder implements IPlayerHeaderPresenter.PlayerView {

    private static final String LIKE = "like";
    private static final String DISLIKE = "dislike";
    private static final String NONE = "none";

    @Inject
    IPlayerHeaderPresenter<IPlayerHeaderPresenter.PlayerView> presenter;

    @BindView(R.id.like_btn) ImageButton likeButton;

    @BindView(R.id.dislike_btn) ImageButton dislikeButton;

    @BindView(R.id.video_title_tv) TextView videoTitleTv;

    private Context context;

    private String videoId;

    public HeaderViewHolder(View itemView, Context context) {
        super(itemView);

        this.context = context;
        ButterKnife.bind(this, itemView);
        inject();
    }

    private void inject() {
        if (context instanceof PlayerActivity) {
            ((PlayerActivity) context).getPlayerComponent().inject(this);
        }
    }

    void bindHeaderViewHolder(String videoId, String videoTitle) {
        this.videoId = videoId;
        videoTitleTv.setText(videoTitle);

        presenter.setView(this);
        presenter.getVideoRating(videoId);
    }

    @OnClick(R.id.like_btn)
    public void onLikeClick() {
        if (likeButton.getColorFilter() != null) {
            likeButton.setColorFilter(null);
            presenter.rateVideo(videoId, NONE);
        } else {
            likeButton.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            dislikeButton.setColorFilter(null);
            presenter.rateVideo(videoId, LIKE);
        }
    }


    @OnClick(R.id.dislike_btn)
    public void onDislikeClick() {
        if (dislikeButton.getColorFilter() != null) {
            dislikeButton.setColorFilter(null);
            presenter.rateVideo(videoId, NONE);
        } else {
            dislikeButton.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            likeButton.setColorFilter(null);
            presenter.rateVideo(videoId, DISLIKE);
        }
    }

    @Override
    public void displayRating(String rating) {
        if (rating.equals(LIKE)) {
            likeButton.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        } else if (rating.equals(DISLIKE)) {
            dislikeButton.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        }
    }
}
