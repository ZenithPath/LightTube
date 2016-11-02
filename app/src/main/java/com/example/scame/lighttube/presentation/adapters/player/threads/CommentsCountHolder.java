package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.scame.lighttube.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsCountHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.comments_count_tv) TextView commentsCountTv;

    @BindView(R.id.comments_mode_ib) ImageButton modeImageButton;

    public CommentsCountHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bindView(List<?> items, int position) {
        if (items.get(position) instanceof Integer) {
            int commentsCount = (Integer) items.get(position);
            String format = commentsCountTv.getResources().getString(R.string.comments);
            commentsCountTv.setText(String.format(format, commentsCount));
        }
    }
}
