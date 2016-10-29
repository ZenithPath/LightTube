package com.example.scame.lighttube.presentation.adapters.player.replies;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.fragments.RepliesFragment;

import java.util.List;

public class RepliesInputDelegate implements AdapterDelegate<List<?>> {

    private RepliesFragment.RepliesListener replyInputListener;

    private boolean asReply;

    private String authorName;

    public RepliesInputDelegate(RepliesFragment.RepliesListener replyInputListener) {
        this.replyInputListener = replyInputListener;
    }

    public void setModeFields(boolean asReply, String authorName) {
        this.asReply = asReply;
        this.authorName = authorName;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_input_item, parent, false);
        return new ReplyInputViewHolder(replyInputListener, itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull List<?> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof ReplyInputViewHolder && asReply) {
            ReplyInputViewHolder replyInputViewHolder = (ReplyInputViewHolder) holder;
            replyInputViewHolder.giveFocus(authorName);
            setModeFields(false, null);
        }
    }

    @Override
    public int getViewType() {
        return RepliesDelegatesManager.VIEW_TYPE_REPLY_INPUT;
    }
}
