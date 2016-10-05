package com.example.scame.lighttube.presentation.adapters.player;


import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.fragments.PlayerFooterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

class HeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.like_btn) ImageButton likeButton;

    @BindView(R.id.dislike_btn) ImageButton dislikeButton;

    @BindView(R.id.video_title_tv) TextView videoTitleTv;

    private Context context;

    HeaderViewHolder(View itemView, Context context) {
        super(itemView);

        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    void bindHeaderViewHolder(String videoTitle, PlayerFooterFragment.LikeListener likeListener,
                                            PlayerFooterFragment.DislikeListener dislikeListener) {

        videoTitleTv.setText(videoTitle);
        likeButton.setOnClickListener((view) -> likeClickHandler(likeListener));
        dislikeButton.setOnClickListener((view) -> dislikeClickHandler(dislikeListener));
    }

    private void likeClickHandler(PlayerFooterFragment.LikeListener likeListener) {
        if (likeButton.getColorFilter() != null) {
            likeButton.setColorFilter(null);
            likeListener.onLikeClick(false);
        } else {
            likeButton.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            dislikeButton.setColorFilter(null);
            likeListener.onLikeClick(true);
        }
    }

    private void dislikeClickHandler(PlayerFooterFragment.DislikeListener dislikeListener) {
        if (dislikeButton.getColorFilter() != null) {
            dislikeButton.setColorFilter(null);
            dislikeListener.onDislikeClick(false);
        } else {
            dislikeButton.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            likeButton.setColorFilter(null);
            dislikeListener.onDislikeClick(true);
        }
    }
}
