package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.PopupHandler;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;
import com.example.scame.lighttube.presentation.fragments.PlayerFooterFragment;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsViewHolder extends RecyclerView.ViewHolder {

    private static final int SECOND_REPLY_POS = 0;
    private static final int FIRST_REPLY_POS = 1;

    private static int IMAGE_SIZE;

    @BindView(R.id.thread_comment_root) GridLayout threadRoot;
    @BindView(R.id.first_reply_root) GridLayout firstReplyRoot;
    @BindView(R.id.second_reply_root) GridLayout secondReplyRoot;
    @BindView(R.id.all_replies_tv) TextView allRepliesTv;
    @BindView(R.id.reply_input_view) EditText replyInputView;

    // threadRoot's views
    private ImageView threadProfileIv;
    private TextView threadCommentText;
    private TextView threadCommentDate;
    private TextView threadCommentAuthor;
    private ImageButton threadMenuOptions;
    private ImageButton repliesIb;
    private TextView repliesCount;

    // firstReplyRoot's views
    private ImageView firstReplyProfileIv;
    private TextView firstReplyText;
    private TextView firstReplyDate;
    private TextView firstReplyAuthor;
    private ImageButton firstReplyMenuOptions;

    // secondReplyRoot's views
    private ImageView secondReplyProfileIv;
    private TextView secondReplyText;
    private TextView secondReplyDate;
    private TextView secondReplyAuthor;
    private ImageButton secondReplyMenuOptions;

    private PopupHandler popupHandler;

    private String identifier;

    private CommentActionListener commentActionListener;

    private PlayerFooterFragment.PlayerFooterListener footerListener;

    public CommentsViewHolder(View itemView, String identifier, CommentActionListener commentActionListener,
                              PlayerFooterFragment.PlayerFooterListener footerListener) {
        super(itemView);

        this.footerListener = footerListener;
        popupHandler = new PopupHandler(commentActionListener, identifier);
        this.commentActionListener = commentActionListener;
        this.identifier = identifier;
        IMAGE_SIZE = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.profile_image_size);
        ButterKnife.bind(this, itemView);
        findViews();
    }

    private void setAllRepliesClickListener(PlayerFooterFragment.PlayerFooterListener footerListener,
                                            List<ThreadCommentModel> comments) {
        allRepliesTv.setOnClickListener(v -> {
                    int position = getAdapterPosition() - CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE;
                    footerListener.onRepliesClick(comments.get(position), identifier);
                });
    }

    private void findViews() {
        threadProfileIv = ButterKnife.findById(threadRoot, R.id.profile_iv);
        threadCommentText = ButterKnife.findById(threadRoot, R.id.comment_text_tv);
        threadCommentDate = ButterKnife.findById(threadRoot, R.id.comment_date_tv);
        threadCommentAuthor = ButterKnife.findById(threadRoot, R.id.author_name_tv);
        repliesIb = ButterKnife.findById(threadRoot, R.id.replies_iv);
        threadMenuOptions = ButterKnife.findById(threadRoot, R.id.more_option_ib);
        repliesCount = ButterKnife.findById(threadRoot, R.id.replies_count);

        firstReplyProfileIv = ButterKnife.findById(firstReplyRoot, R.id.profile_iv);
        firstReplyText = ButterKnife.findById(firstReplyRoot, R.id.comment_text_tv);
        firstReplyDate = ButterKnife.findById(firstReplyRoot, R.id.comment_date_tv);
        firstReplyAuthor = ButterKnife.findById(firstReplyRoot, R.id.author_name_tv);
        firstReplyMenuOptions = ButterKnife.findById(firstReplyRoot, R.id.more_option_ib);

        secondReplyProfileIv = ButterKnife.findById(secondReplyRoot, R.id.profile_iv);
        secondReplyText = ButterKnife.findById(secondReplyRoot, R.id.comment_text_tv);
        secondReplyDate = ButterKnife.findById(secondReplyRoot, R.id.comment_date_tv);
        secondReplyAuthor = ButterKnife.findById(secondReplyRoot, R.id.author_name_tv);
        secondReplyMenuOptions = ButterKnife.findById(secondReplyRoot, R.id.more_option_ib);
    }

    void bindThreadCommentView(int position, List<ThreadCommentModel> comments) {
        bindThreadUtil(position, comments);

        firstReplyRoot.setVisibility(View.GONE);
        secondReplyRoot.setVisibility(View.GONE);
        allRepliesTv.setVisibility(View.GONE);
    }

    void bindOneReplyView(int position, List<ThreadCommentModel> comments) {
        bindThreadUtil(position, comments);
        bindFirstReplyUtil(position, comments, SECOND_REPLY_POS);

        secondReplyRoot.setVisibility(View.GONE);
        allRepliesTv.setVisibility(View.GONE);
    }

    void bindTwoRepliesView(int position, List<ThreadCommentModel> comments) {
        bindThreadUtil(position, comments);
        bindFirstReplyUtil(position, comments, FIRST_REPLY_POS);
        bindSecondReplyUtil(position, comments, SECOND_REPLY_POS);

        allRepliesTv.setVisibility(View.GONE);
    }

    void bindAllRepliesView(int position, List<ThreadCommentModel> comments) {
        setAllRepliesClickListener(footerListener, comments); // not the best place
        bindThreadUtil(position, comments);
        bindFirstReplyUtil(position, comments, FIRST_REPLY_POS);
        bindSecondReplyUtil(position, comments, SECOND_REPLY_POS);
    }

    private void bindThreadUtil(int position, List<ThreadCommentModel> comments) {
        ThreadCommentModel commentModel = comments.get(position);

        handleThreadCommentPopup(commentModel);
        bindRepliesSection(position, commentModel);

        Picasso.with(threadProfileIv.getContext())
                .load(commentModel.getProfileImageUrl())
                .noFade().resize(IMAGE_SIZE, IMAGE_SIZE).centerCrop()
                .placeholder(R.drawable.placeholder_grey)
                .into(threadProfileIv);

        threadCommentText.setText(commentModel.getTextDisplay());
        threadCommentDate.setText(commentModel.getDate());
        threadCommentAuthor.setText(commentModel.getAuthorName());
    }

    private void bindRepliesSection(int position, ThreadCommentModel commentModel) {
        repliesIb.setImageDrawable(repliesIb.getContext().getResources()
                .getDrawable(R.drawable.ic_question_answer_black_24dp));

        repliesIb.setOnClickListener(v -> commentActionListener.onActionReplyClick(commentModel.getThreadId(),
                new Pair<>(position, -1)));

        if (commentModel.getReplyCount() != 0) {
            repliesCount.setText(String.valueOf(commentModel.getReplyCount()));
        }
    }

    private void bindFirstReplyUtil(int position, List<ThreadCommentModel> comments, int index) {
        ReplyModel replyModel = comments.get(position)
                .getReplies().get(index);

        handleReplyPopup(firstReplyMenuOptions, replyModel, index);

        Picasso.with(firstReplyProfileIv.getContext())
                .load(replyModel.getProfileImageUrl())
                .noFade().resize(IMAGE_SIZE, IMAGE_SIZE).centerCrop()
                .placeholder(R.drawable.placeholder_grey)
                .into(firstReplyProfileIv);

        firstReplyText.setText(replyModel.getTextDisplay());
        firstReplyDate.setText(replyModel.getDate());
        firstReplyAuthor.setText(replyModel.getAuthorName());
    }


    private void bindSecondReplyUtil(int position, List<ThreadCommentModel> comments, int index) {
        ReplyModel replyModel = comments.get(position)
                .getReplies().get(index);

        handleReplyPopup(secondReplyMenuOptions, replyModel, index);

        Picasso.with(secondReplyProfileIv.getContext())
                .load(replyModel.getProfileImageUrl())
                .noFade().resize(IMAGE_SIZE, IMAGE_SIZE).centerCrop()
                .placeholder(R.drawable.placeholder_grey)
                .into(secondReplyProfileIv);

        secondReplyText.setText(replyModel.getTextDisplay());
        secondReplyDate.setText(replyModel.getDate());
        secondReplyAuthor.setText(replyModel.getAuthorName());
    }

    private void handleThreadCommentPopup(ThreadCommentModel commentModel) {
        threadMenuOptions.setOnClickListener(v -> {
            int datasetIndex = getAdapterPosition() - CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE;
            Pair<Integer, Integer> commentIndex = new Pair<>(datasetIndex, -1);
            popupHandler.showPopup(v, commentModel.getAuthorChannelId(), commentModel.getThreadId(), commentIndex);
        });
    }

    private void handleReplyPopup(ImageButton menuOptions, ReplyModel replyModel, int replyPosition) {
        menuOptions.setOnClickListener(v -> {
            int datasetIndex = getAdapterPosition() - CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE;
            Pair<Integer, Integer> commentIndex = new Pair<>(datasetIndex, replyPosition);
            popupHandler.showPopup(v, replyModel.getAuthorChannelId(), replyModel.getCommentId(), commentIndex);
        });
    }

    public void giveFocusToInputField(View.OnClickListener clickListener, String target) {
        replyInputView.setVisibility(View.VISIBLE);

        if (replyInputView.requestFocus()) {
            replyInputView.setText("");
            replyInputView.append("+" + target + " ");

            InputMethodManager imm = (InputMethodManager) replyInputView.getContext().
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }

        setOnEditActionListener(clickListener);
    }

    private void setOnEditActionListener(View.OnClickListener listener) {
        replyInputView.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;

            if (actionId == EditorInfo.IME_ACTION_SEND) {
                listener.onClick(replyInputView);
                handled = true;
                replyInputView.setVisibility(View.GONE);
            }

            return handled;
        });
    }
}
