package com.example.scame.lighttube.presentation.adapters.player;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.fragments.PlayerFooterFragment;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class CommentsViewHolder extends RecyclerView.ViewHolder {

    private static final int LAST_REPLY = 0;
    private static final int PENULTIMATE_REPLY = 1;

    private static int IMAGE_SIZE;

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

        IMAGE_SIZE = context.getResources().getDimensionPixelSize(R.dimen.profile_image_size);

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
        bindFirstReplyUtil(position, comments, LAST_REPLY);

        secondReplyRoot.setVisibility(View.GONE);
        allRepliesTv.setVisibility(View.GONE);
    }

    void bindTwoRepliesView(int position, List<ThreadCommentModel> comments) {
        bindThreadUtil(position, comments);
        bindFirstReplyUtil(position, comments, PENULTIMATE_REPLY);
        bindSecondReplyUtil(position, comments, LAST_REPLY);

        allRepliesTv.setVisibility(View.GONE);
    }

    void bindAllRepliesView(int position, List<ThreadCommentModel> comments,
                            PlayerFooterFragment.PlayerFooterListener footerListener) {

        String commentThreadId = comments.get(position - CommentsAdapter.VIEW_ABOVE_NUMBER).getThreadId();
        allRepliesTv.setOnClickListener(v -> footerListener.onRepliesClick(commentThreadId));

        bindThreadUtil(position, comments);
        bindFirstReplyUtil(position, comments, PENULTIMATE_REPLY);
        bindSecondReplyUtil(position, comments, LAST_REPLY);
    }

    private void bindThreadUtil(int position, List<ThreadCommentModel> comments) {
        ThreadCommentModel commentModel = comments.get(position - CommentsAdapter.VIEW_ABOVE_NUMBER);

        Picasso.with(context).load(commentModel.getProfileImageUrl())
                .noFade().resize(IMAGE_SIZE, IMAGE_SIZE).centerCrop()
                .placeholder(R.drawable.placeholder_grey)
                .into(threadProfileIv);

        threadCommentText.setText(commentModel.getTextDisplay());
        threadCommentDate.setText(commentModel.getDate());
        threadCommentAuthor.setText(commentModel.getAuthorName());
        // add replies IV
    }

    private void bindFirstReplyUtil(int position, List<ThreadCommentModel> comments, int index) {
        ReplyModel replyModel = comments.get(position - CommentsAdapter.VIEW_ABOVE_NUMBER)
                .getReplies().get(index);

        Picasso.with(context).load(replyModel.getProfileImageUrl())
                .noFade().resize(IMAGE_SIZE, IMAGE_SIZE).centerCrop()
                .placeholder(R.drawable.placeholder_grey)
                .into(firstReplyProfileIv);

        firstReplyText.setText(replyModel.getTextDisplay());
        firstReplyDate.setText(replyModel.getDate());
        firstReplyAuthor.setText(replyModel.getAuthorName());
    }

    private void bindSecondReplyUtil(int position, List<ThreadCommentModel> comments, int index) {
        ReplyModel replyModel = comments.get(position - CommentsAdapter.VIEW_ABOVE_NUMBER)
                .getReplies().get(index);

        Picasso.with(context).load(replyModel.getProfileImageUrl())
                .noFade().resize(IMAGE_SIZE, IMAGE_SIZE).centerCrop()
                .placeholder(R.drawable.placeholder_grey)
                .into(secondReplyProfileIv);

        secondReplyText.setText(replyModel.getTextDisplay());
        secondReplyDate.setText(replyModel.getDate());
        secondReplyAuthor.setText(replyModel.getAuthorName());
    }
}
