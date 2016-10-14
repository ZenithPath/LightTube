package com.example.scame.lighttube.presentation.adapters.player;


import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.LightTubeApp;
import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.presenters.IVideoRatingPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HeaderViewHolder extends RecyclerView.ViewHolder implements IVideoRatingPresenter.PlayerView {

    private static final String LIKE = "like";
    private static final String DISLIKE = "dislike";
    private static final String NONE = "none";

    @Inject
    IVideoRatingPresenter<IVideoRatingPresenter.PlayerView> presenter;

    @BindView(R.id.like_btn) ImageButton likeButton;

    @BindView(R.id.dislike_btn) ImageButton dislikeButton;

    @BindView(R.id.video_title_tv) TextView videoTitleTv;

    private String videoId;

    private String identifier;

    public HeaderViewHolder(View itemView, Context context, String videoId, String videoTitle, String identifier) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        this.videoId = videoId;
        this.identifier = identifier;
        videoTitleTv.setText(videoTitle);
        
        inject(context);
        presenter.setView(this);
        presenter.getVideoRating(videoId);
    }

    private void inject(Context context) {
        if (context instanceof PlayerActivity) {
            ((PlayerActivity) context).getPlayerFooterComponent().inject(this);
        }
    }

    @OnClick(R.id.like_btn)
    public void onLikeClick() {
        if (likeButton.getColorFilter() != null) {
            likeButton.setColorFilter(null);
            presenter.rateVideo(videoId, NONE);
        } else {
            applyColorFilter(likeButton);
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
            applyColorFilter(dislikeButton);
            likeButton.setColorFilter(null);
            presenter.rateVideo(videoId, DISLIKE);
        }
    }

    @Override
    public void displayRating(String rating) {
        if (rating.equals(LIKE)) {
            applyColorFilter(likeButton);
        } else if (rating.equals(DISLIKE)) {
            applyColorFilter(dislikeButton);
        }
    }

    private void applyColorFilter(ImageButton imageButton) {
        imageButton.setColorFilter(LightTubeApp.getAppComponent().getContext()
                .getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
    }
}
