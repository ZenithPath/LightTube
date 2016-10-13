package com.example.scame.lighttube.presentation.adapters.player;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.fragments.RepliesFragment;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

// TODO: refactor, memory leaks, delegates
public class RepliesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_ABOVE_NUMBER = 1;

    private static final int REPLY_INPUT_POS = 0;

    private static final int VIEW_TYPE_REPLY_COMMENT = 0;
    private static final int VIEW_TYPE_REPLY_INPUT = 1;

    private RepliesFragment.RepliesListener repliesListener;

    private ReplyListModel replies;

    private Context context;

    public RepliesAdapter(ReplyListModel replies, Context context, RepliesFragment.RepliesListener repliesListener) {
        this.replies = replies;
        this.context = context;
        this.repliesListener = repliesListener;
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

        public void bindRepliesView(int position, ReplyListModel replies, Context context) {
            ReplyModel replyModel = replies.getReplyModel(position);

            Picasso.with(context).load(replyModel.getProfileImageUrl())
                    .noFade().resize(30, 30).centerCrop()
                    .placeholder(R.drawable.placeholder_grey)
                    .into(profileImage);

            commentText.setText(replyModel.getTextDisplay());
            authorName.setText(replyModel.getAuthorName());
            commentDate.setText(replyModel.getDate());
        }
    }

    public static class ReplyInputViewHolder extends RecyclerView.ViewHolder implements IReplyInputPresenter.ReplyView {

        @Inject
        IReplyInputPresenter<IReplyInputPresenter.ReplyView> presenter;

        @BindView(R.id.comment_input) EditText replyInput;

        private RepliesFragment.RepliesListener repliesListener;

        private Context context;

        public ReplyInputViewHolder(View itemView, Context context) {
            super(itemView);

            this.context = context;
            ButterKnife.bind(this, itemView);
            inject();
        }

        private void inject() {
            if (context instanceof PlayerActivity) {
                ((PlayerActivity) context).getPlayerComponent().inject(this);
            }
        }

        public void bindReplyInputView(String parentId, RepliesFragment.RepliesListener repliesListener) {
            this.repliesListener = repliesListener;

            replyInput.setOnEditorActionListener((v, actionId, event) -> {
                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    presenter.setView(this);
                    presenter.postReply(parentId, replyInput.getText().toString());
                    handled = true;
                }

                return handled;
            });
        }

        @Override
        public void displayReply(ReplyModel replyModel) {
            if (repliesListener != null) {
                replyInput.setText("");
                repliesListener.onPostedReply(replyModel);
            }
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_REPLY_INPUT) {
            View replyInputView = inflater.inflate(R.layout.comment_input_item, parent, false);
            viewHolder = new ReplyInputViewHolder(replyInputView, context);
        } else if (viewType == VIEW_TYPE_REPLY_COMMENT) {
            View replyView = inflater.inflate(R.layout.comment_item, parent, false);
            viewHolder = new RepliesViewHolder(replyView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReplyInputViewHolder) {
            ReplyInputViewHolder viewHolder = (ReplyInputViewHolder) holder;
            viewHolder.bindReplyInputView(replies.getReplyModel(0).getParentId(), repliesListener);
        } else if (holder instanceof RepliesViewHolder) {
            RepliesViewHolder viewHolder = (RepliesViewHolder) holder;
            viewHolder.bindRepliesView(position - VIEW_ABOVE_NUMBER, replies, context);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == REPLY_INPUT_POS) {
            return VIEW_TYPE_REPLY_INPUT;
        } else {
            return VIEW_TYPE_REPLY_COMMENT;
        }
    }

    @Override
    public int getItemCount() {
        return replies.getReplyModels().size() + VIEW_ABOVE_NUMBER;
    }
}
