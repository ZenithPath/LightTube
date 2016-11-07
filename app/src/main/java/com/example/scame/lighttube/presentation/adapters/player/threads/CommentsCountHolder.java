package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.repository.CommentsRepositoryImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsCountHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.comments_count_tv) TextView commentsCountTv;

    @BindView(R.id.comments_mode_ib) ImageButton modeImageButton;

    public CommentsCountHolder(View itemView, View.OnClickListener orderListener) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        modeImageButton.setOnClickListener(v -> showOrderPopup(v, orderListener));
    }

    private void showOrderPopup(View view, View.OnClickListener orderListener) {
        PopupMenu orderPopup = new PopupMenu(view.getContext(), view);
        orderPopup.getMenuInflater().inflate(R.menu.order_popup, orderPopup.getMenu());

        orderPopup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.relevance_option) {
                view.setTag(CommentsRepositoryImp.RELEVANCE_ORDER);
                orderListener.onClick(view);
                return true;
            } else if (item.getItemId() == R.id.time_option) {
                view.setTag(CommentsRepositoryImp.TIME_ORDER);
                orderListener.onClick(view);
                return true;
            }

            return false;
        });

        orderPopup.show();
    }

    public void bindView(List<?> items, int position) {
        if (items.get(position) instanceof Integer) {
            int commentsCount = (Integer) items.get(position);
            String format = commentsCountTv.getResources().getString(R.string.comments);
            commentsCountTv.setText(String.format(format, commentsCount));
        }
    }
}
