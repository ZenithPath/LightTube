package com.example.scame.lighttube.presentation.adapters.player;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RepliesAdapter extends RecyclerView.Adapter<RepliesAdapter.RepliesViewHolder> {

    private ReplyListModel replies;

    private Context context;

    public RepliesAdapter(ReplyListModel replies, Context context) {
        this.replies = replies;
        this.context = context;
    }

    public static class RepliesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.profile_iv) CircleImageView profileImage;

        @BindView(R.id.comment_text_tv) TextView commentText;

        @BindView(R.id.author_name_tv) TextView authorName;

        @BindView(R.id.comment_date_tv) TextView commentDate;

        public RepliesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RepliesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View replyView = inflater.inflate(R.layout.comment_item, parent, false);

        return new RepliesViewHolder(replyView);
    }

    @Override
    public void onBindViewHolder(RepliesViewHolder holder, int position) {
        ReplyModel replyModel = replies.getReplyModel(position);

        Picasso.with(context).load(replyModel.getProfileImageUrl())
                .noFade().resize(30, 30).centerCrop()
                .placeholder(R.drawable.placeholder_grey)
                .into(holder.profileImage);

        holder.commentText.setText(replyModel.getTextDisplay());
        holder.authorName.setText(replyModel.getAuthorName());
        holder.commentDate.setText(replyModel.getDate());
    }

    @Override
    public int getItemCount() {
        return replies.getReplyModels().size();
    }
}
