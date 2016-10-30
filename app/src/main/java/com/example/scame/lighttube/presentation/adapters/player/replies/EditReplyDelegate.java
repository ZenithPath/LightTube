package com.example.scame.lighttube.presentation.adapters.player.replies;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;

import java.util.List;

import static com.example.scame.lighttube.presentation.adapters.player.replies.RepliesDelegatesManager.VIEW_TYPE_EDIT_REPLY;

public class EditReplyDelegate implements AdapterDelegate<List<?>> {

    private CommentActionListener commentActionListener;

    public EditReplyDelegate (CommentActionListener commentActionListener) {
        this.commentActionListener = commentActionListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_input_item, parent, false);
        return new EditCommentViewHolder(itemView, commentActionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull List<?> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof EditCommentViewHolder) {
            // position -1 means that we are editing a primary comment
            int relativePosition = position == -1 ? RepliesDelegatesManager.HEADER_COMMENT_POS : position;
            UpdateReplyObj replyModelHolder = (UpdateReplyObj) items.get(relativePosition);

            ((EditCommentViewHolder) holder).bindCommentEditorHolder(
                    replyModelHolder.getTextToUpdate(), replyModelHolder.getCommentId()
            );
        }
    }

    @Override
    public int getViewType() {
        return VIEW_TYPE_EDIT_REPLY;
    }
}
