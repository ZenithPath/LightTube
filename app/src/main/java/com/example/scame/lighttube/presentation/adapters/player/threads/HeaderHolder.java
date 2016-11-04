package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.LightTubeApp;
import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.model.HeaderModel;
import com.example.scame.lighttube.presentation.presenters.IVideoRatingPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HeaderHolder extends RecyclerView.ViewHolder implements IVideoRatingPresenter.PlayerView {

    private static final String LIKE = "like";
    private static final String DISLIKE = "dislike";
    private static final String NONE = "none";

    @Inject
    IVideoRatingPresenter<IVideoRatingPresenter.PlayerView> presenter;

    @BindView(R.id.like_btn) ImageButton likeButton;

    @BindView(R.id.dislike_btn) ImageButton dislikeButton;

    @BindView(R.id.video_title_tv) TextView videoTitleTv;

    @BindView(R.id.description_container) GridLayout descriptionContainer;

    @BindView(R.id.description_ib) ImageButton descriptionButton;

    @BindView(R.id.view_count_tv) TextView viewCountTv;

    @BindView(R.id.video_description_tv) TextView descriptionTv;

    @BindView(R.id.category_description_tv) TextView categoryTv;

    @BindView(R.id.license_description_tv) TextView licenseTv;

    @BindView(R.id.dislike_count) TextView dislikeCountTv;

    @BindView(R.id.like_count) TextView likeCountTv;

    private HeaderModel headerModel;

    public HeaderHolder(View itemView, Context context, HeaderModel headerModel) {
        super(itemView);

        this.headerModel = headerModel;
        setupViews(itemView);

        inject(context);
        presenter.setView(this);
        presenter.getVideoRating(headerModel.getVideoId());
    }

    private void setupViews(View itemView) {
        ButterKnife.bind(this, itemView);

        videoTitleTv.setText(headerModel.getVideoTitle());

        String formattedViewCount = String.format(videoTitleTv.getContext()
                .getString(R.string.view_count), headerModel.getViewCount());
        viewCountTv.setText(formattedViewCount);

        descriptionTv.setText(headerModel.getVideoDescription());
        categoryTv.setText(headerModel.getVideoCategory());
        licenseTv.setText(headerModel.getLicense());

        dislikeCountTv.setText(String.valueOf(headerModel.getDislikeCount()));
        likeCountTv.setText(String.valueOf(headerModel.getLikeCount()));

        descriptionButton.setOnClickListener(v -> {
            if (descriptionContainer.getVisibility() == View.GONE) {
                descriptionContainer.setVisibility(View.VISIBLE);
                descriptionButton.setImageDrawable(v.getContext().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
            } else {
                descriptionContainer.setVisibility(View.GONE);
                descriptionButton.setImageDrawable(v.getContext().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
            }
        });
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
            presenter.rateVideo(headerModel.getVideoId(), NONE);
        } else {
            applyColorFilter(likeButton);
            dislikeButton.setColorFilter(null);
            presenter.rateVideo(headerModel.getVideoId(), LIKE);
        }
    }


    @OnClick(R.id.dislike_btn)
    public void onDislikeClick() {
        if (dislikeButton.getColorFilter() != null) {
            dislikeButton.setColorFilter(null);
            presenter.rateVideo(headerModel.getVideoId(), NONE);
        } else {
            applyColorFilter(dislikeButton);
            likeButton.setColorFilter(null);
            presenter.rateVideo(headerModel.getVideoId(), DISLIKE);
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
