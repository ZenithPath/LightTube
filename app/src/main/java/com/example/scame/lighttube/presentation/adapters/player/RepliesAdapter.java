package com.example.scame.lighttube.presentation.adapters.player;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;
import com.example.scame.lighttube.presentation.fragments.RepliesFragment;
import com.example.scame.lighttube.presentation.model.ReplyListModel;


public class RepliesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_ABOVE_NUMBER = 1;

    private static final int REPLY_INPUT_POS = 0;

    private static final int VIEW_TYPE_REPLY_COMMENT = 0;
    private static final int VIEW_TYPE_REPLY_INPUT = 1;

    private RepliesFragment.RepliesListener repliesListener;

    private CommentActionListener commentActionListener;

    private ReplyListModel replies;

    private String userIdentifier;

    private Context context;

    public RepliesAdapter(CommentActionListener commentActionListener, ReplyListModel replies,
                          Context context, RepliesFragment.RepliesListener repliesListener,
                          String userIdentifier) {
        this.replies = replies;
        this.context = context;
        this.userIdentifier = userIdentifier;
        this.repliesListener = repliesListener;
        this.commentActionListener = commentActionListener;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_REPLY_INPUT) {
            View replyInputView = inflater.inflate(R.layout.comment_input_item, parent, false);
            String parentId = replies.getReplyModel(0).getParentId();
            viewHolder = new ReplyInputViewHolder(repliesListener, replyInputView,
                    context, parentId, userIdentifier);
        } else if (viewType == VIEW_TYPE_REPLY_COMMENT) {
            View replyView = inflater.inflate(R.layout.comment_item, parent, false);
            viewHolder = new RepliesViewHolder(commentActionListener, replyView, userIdentifier);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RepliesViewHolder) {
            RepliesViewHolder viewHolder = (RepliesViewHolder) holder;
            viewHolder.bindRepliesView(position - VIEW_ABOVE_NUMBER, replies);
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
