package com.example.scame.lighttube.presentation.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.scame.lighttube.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.progress_item_pb) ProgressBar progressBar;

    public ProgressViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bindProgressView() {
        progressBar.setIndeterminate(true);
    }
}
