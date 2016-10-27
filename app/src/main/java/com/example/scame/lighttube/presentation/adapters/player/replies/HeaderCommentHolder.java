package com.example.scame.lighttube.presentation.adapters.player.replies;


import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.PopupHandler;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HeaderCommentHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.profile_iv) CircleImageView profileImage;

    @BindView(R.id.comment_text_tv) TextView commentText;

    @BindView(R.id.author_name_tv) TextView authorName;

    @BindView(R.id.comment_date_tv) TextView commentDate;

    @BindView(R.id.more_option_ib) ImageButton menuOptions;

    private PopupHandler popupHandler;

    public HeaderCommentHolder(View itemView, PopupHandler popupHandler) {
        super(itemView);
        this.popupHandler = popupHandler;
        ButterKnife.bind(this, itemView);
    }

    public void bindHeaderCommentHolder(ThreadCommentModel model) {
        handlePopup(model);

        Picasso.with(profileImage.getContext()).load(model.getProfileImageUrl())
                .noFade().resize(48, 48).centerCrop()
                .placeholder(R.drawable.placeholder_grey)
                .into(profileImage);

        commentText.setText(model.getTextDisplay());
        authorName.setText(model.getAuthorName());
        commentDate.setText(model.getDate());
    }

    private void handlePopup(ThreadCommentModel model) {
        menuOptions.setOnClickListener(v -> {
            int position = RepliesDelegatesManager.HEADER_COMMENT_POS;
            Pair<Integer, Integer> commentIndex = new Pair<>(position, -1);
            popupHandler.showPopup(menuOptions, model.getAuthorChannelId(), model.getThreadId(), commentIndex);
        });
    }
}
