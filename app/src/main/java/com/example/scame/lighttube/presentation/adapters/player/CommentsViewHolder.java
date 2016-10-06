package com.example.scame.lighttube.presentation.adapters.player;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class CommentsViewHolder extends RecyclerView.ViewHolder {

    private static final int LAST_REPLY = 0;
    private static final int PENULTIMATE_REPLY = 1;

    @BindView(R.id.thread_comment_root) GridLayout threadRoot;
    @BindView(R.id.first_reply_root) GridLayout firstReplyRoot;
    @BindView(R.id.second_reply_root) GridLayout secondReplyRoot;
    @BindView(R.id.all_replies_tv) TextView allRepliesTv;

    // threadRoot's views
    private ImageView threadProfileIv;
    private TextView threadCommentText;
    private TextView threadCommentDate;
    private TextView threadCommentAuthor;
    private ImageView repliesIv;

    // firstReplyRoot's views
    private ImageView firstReplyProfileIv;
    private TextView firstReplyText;
    private TextView firstReplyDate;
    private TextView firstReplyAuthor;

    // secondReplyRoot's views
    private ImageView secondReplyProfileIv;
    private TextView secondReplyText;
    private TextView secondReplyDate;
    private TextView secondReplyAuthor;

    private Context context;

    CommentsViewHolder(View itemView, Context context) {
        super(itemView);

        this.context = context;
        ButterKnife.bind(this, itemView);

        threadProfileIv = ButterKnife.findById(threadRoot, R.id.profile_iv);
        threadCommentText = ButterKnife.findById(threadRoot, R.id.comment_text_tv);
        threadCommentDate = ButterKnife.findById(threadRoot, R.id.comment_date_tv);
        threadCommentAuthor = ButterKnife.findById(threadRoot, R.id.author_name_tv);
        repliesIv = ButterKnife.findById(threadRoot, R.id.replies_iv);

        firstReplyProfileIv = ButterKnife.findById(firstReplyRoot, R.id.profile_iv);
        firstReplyText = ButterKnife.findById(firstReplyRoot, R.id.comment_text_tv);
        firstReplyDate = ButterKnife.findById(firstReplyRoot, R.id.comment_date_tv);
        firstReplyAuthor = ButterKnife.findById(firstReplyRoot, R.id.author_name_tv);

        secondReplyProfileIv = ButterKnife.findById(secondReplyRoot, R.id.profile_iv);
        secondReplyText = ButterKnife.findById(secondReplyRoot, R.id.comment_text_tv);
        secondReplyDate = ButterKnife.findById(secondReplyRoot, R.id.comment_date_tv);
        secondReplyAuthor = ButterKnife.findById(secondReplyRoot, R.id.author_name_tv);
    }

    void bindThreadCommentView(int position, List<ThreadCommentModel> comments) {
        bindThreadUtil(position, comments);

        firstReplyRoot.setVisibility(View.GONE);
        secondReplyRoot.setVisibility(View.GONE);
        allRepliesTv.setVisibility(View.GONE);
    }

    void bindOneReplyView(int position, List<ThreadCommentModel> comments) {
        bindThreadUtil(position, comments);
        bindFirstReplyUtil(position, comments);

        secondReplyRoot.setVisibility(View.GONE);
        allRepliesTv.setVisibility(View.GONE);
    }

    void bindTwoRepliesView(int position, List<ThreadCommentModel> comments) {
        bindThreadUtil(position, comments);
        bindFirstReplyUtil(position, comments);
        bindSecondReplyUtil(position, comments);

        allRepliesTv.setVisibility(View.GONE);
    }

    void bindAllRepliesView(int position, List<ThreadCommentModel> comments) {
        bindThreadUtil(position, comments);
        bindFirstReplyUtil(position, comments);
        bindSecondReplyUtil(position, comments);
    }

    private void bindThreadUtil(int position, List<ThreadCommentModel> comments) {
        Picasso.with(context).load(comments.get(position - 1).getProfileImageUrl())
                .noFade().resize(30, 30).centerCrop()
                .placeholder(R.drawable.colors_0011_pearl_grey)
                .into(threadProfileIv);

        threadCommentText.setText(comments.get(position - 1).getTextDisplay());
        threadCommentDate.setText(comments.get(position - 1).getDate());
        threadCommentAuthor.setText(comments.get(position - 1).getAuthorName());
        // add replies IV
    }

    private void bindFirstReplyUtil(int position, List<ThreadCommentModel> comments) {
        Picasso.with(context).load(comments.get(position - 1)
                .getReplies().get(LAST_REPLY)
                .getProfileImageUrl())
                .noFade().resize(30, 30).centerCrop()
                .placeholder(R.drawable.colors_0011_pearl_grey)
                .into(firstReplyProfileIv);

        firstReplyText.setText(comments.get(position - 1).getReplies().get(LAST_REPLY).getTextDisplay());
        firstReplyDate.setText(comments.get(position - 1).getReplies().get(LAST_REPLY).getDate());
        firstReplyAuthor.setText(comments.get(position - 1).getReplies().get(LAST_REPLY).getAuthorName());
    }

    private void bindSecondReplyUtil(int position, List<ThreadCommentModel> comments) {
        Picasso.with(context).load(comments.get(position - 1)
                .getReplies().get(PENULTIMATE_REPLY)
                .getProfileImageUrl())
                .noFade().resize(30, 30).centerCrop()
                .placeholder(R.drawable.colors_0011_pearl_grey)
                .into(secondReplyProfileIv);

        secondReplyText.setText(comments.get(position - 1).getReplies().get(PENULTIMATE_REPLY).getTextDisplay());
        secondReplyDate.setText(comments.get(position - 1).getReplies().get(PENULTIMATE_REPLY).getDate());
        secondReplyAuthor.setText(comments.get(position - 1).getReplies().get(PENULTIMATE_REPLY).getAuthorName());
    }
}
