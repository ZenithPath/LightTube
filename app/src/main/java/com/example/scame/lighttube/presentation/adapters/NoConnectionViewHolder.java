package com.example.scame.lighttube.presentation.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.scame.lighttube.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoConnectionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.retry_view_btn) Button retryButton;

    private OnRetryClickListener clickListener;

    public interface OnRetryClickListener {
        void onRetryClick();
    }

    public NoConnectionViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        retryButton.setOnClickListener(view -> clickListener.onRetryClick());
    }

    public void setClickListener(OnRetryClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
