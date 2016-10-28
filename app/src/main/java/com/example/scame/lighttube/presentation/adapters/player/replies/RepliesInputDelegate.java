package com.example.scame.lighttube.presentation.adapters.player.replies;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.fragments.RepliesFragment;
import com.example.scame.lighttube.presentation.model.ReplyListModel;

public class RepliesInputDelegate implements AdapterDelegate<ReplyListModel> {

    private RepliesFragment.RepliesListener replyInputListener;

    public RepliesInputDelegate(RepliesFragment.RepliesListener replyInputListener) {
        this.replyInputListener = replyInputListener;
    }

    private boolean asReply;
    private int replyPosition;

    public void setModeFields(boolean asReply, int position) {
        this.asReply = asReply;
        this.replyPosition = position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_input_item, parent, false);
        return new ReplyInputViewHolder(replyInputListener, itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyListModel items, int position, @NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof ReplyInputViewHolder && asReply) {
            ReplyInputViewHolder replyInputViewHolder = (ReplyInputViewHolder) holder;
            replyInputViewHolder.giveFocus(getAuthorName(position, items));
            setModeFields(false, -1);
        }
    }

    private String getAuthorName(int position, ReplyListModel items) {
        if (position == RepliesDelegatesManager.HEADER_COMMENT_POS) {
            TemporaryPrimaryHolder holder = (TemporaryPrimaryHolder) items
                    .getReplyModel(RepliesDelegatesManager.HEADER_COMMENT_POS);

            return holder.getThreadCommentModel().getAuthorName();
        } else {
            return items.getReplyModel(position).getAuthorName();
        }
    }

    @Override
    public int getViewType() {
        return RepliesDelegatesManager.VIEW_TYPE_REPLY_INPUT;
    }
}
