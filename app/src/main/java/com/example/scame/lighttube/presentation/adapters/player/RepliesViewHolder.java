package com.example.scame.lighttube.presentation.adapters.player;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RepliesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.profile_iv) CircleImageView profileImage;

    @BindView(R.id.comment_text_tv) TextView commentText;

    @BindView(R.id.author_name_tv) TextView authorName;

    @BindView(R.id.comment_date_tv) TextView commentDate;

    @BindView(R.id.more_option_ib) ImageButton menuOptions;


    private PopupHandler popupHandler;

    public RepliesViewHolder(View itemView, String identifier) {
        super(itemView);

        popupHandler = new PopupHandler(identifier);
        ButterKnife.bind(this, itemView);
    }

    public void bindRepliesView(int position, ReplyListModel replies) {
        ReplyModel replyModel = replies.getReplyModel(position);

        menuOptions.setOnClickListener(v -> popupHandler.showPopup(menuOptions, replyModel.getAuthorChannelId()));

        Picasso.with(profileImage.getContext()).load(replyModel.getProfileImageUrl())
                .noFade().resize(30, 30).centerCrop()
                .placeholder(R.drawable.placeholder_grey)
                .into(profileImage);

        commentText.setText(replyModel.getTextDisplay());
        authorName.setText(replyModel.getAuthorName());
        commentDate.setText(replyModel.getDate());
    }
}