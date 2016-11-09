package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.adapters.player.replies.EditCommentViewHolder;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;

import java.util.List;

public class EditCommentDelegate implements AdapterDelegate<List<?>> {

    private CommentActionListener commentActionListener;

    private int viewType;

    public EditCommentDelegate(CommentActionListener commentActionListener, int viewType) {
        this.commentActionListener = commentActionListener;
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_input_item, parent, false);
        return new EditCommentViewHolder(itemView, commentActionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull List<?> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        EditCommentViewHolder editCommentHolder = (EditCommentViewHolder) holder;

        UpdateCommentObj updateCommentHolder = (UpdateCommentObj) items.get(position);
        Pair<Integer, Integer> commentIndex = updateCommentHolder.getPairedPosition();

        String commentId;
        String commentText;

        if (commentIndex.second == -1) {
            commentId = updateCommentHolder.getThreadCommentModel().getThreadId();
            commentText = updateCommentHolder.getThreadCommentModel().getTextDisplay();
        } else {
            commentId = updateCommentHolder.getThreadCommentModel().getReplies().get(commentIndex.second).getCommentId();
            commentText = updateCommentHolder.getThreadCommentModel().getReplies().get(commentIndex.second).getTextDisplay();
        }

        editCommentHolder.bindCommentEditorHolder(commentText, commentId);
    }

    @Override
    public int getViewType() {
        return viewType;
    }
}
