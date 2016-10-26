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
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.List;

public class EditCommentDelegate implements AdapterDelegate<List<ThreadCommentModel>> {

    private CommentActionListener commentActionListener;

    public EditCommentDelegate(CommentActionListener commentActionListener) {
        this.commentActionListener = commentActionListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_input_item, parent, false);
        return new EditCommentViewHolder(itemView, commentActionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull List<ThreadCommentModel> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        EditCommentViewHolder editCommentHolder = (EditCommentViewHolder) holder;

        UpdateCommentModelHolder updateCommentHolder = (UpdateCommentModelHolder) items.get(position);
        Pair<Integer, Integer> commentIndex = updateCommentHolder.getPairedPosition();

        String commentId;
        String commentText;

        if (commentIndex.second == -1) {
            commentId = updateCommentHolder.getThreadId();
            commentText = items.get(commentIndex.first).getTextDisplay();
        } else {
            commentId = updateCommentHolder.getReplies().get(commentIndex.second).getCommentId();
            commentText = updateCommentHolder.getReplies().get(commentIndex.second).getTextDisplay();
        }

        editCommentHolder.bindCommentEditorHolder(commentIndex, commentText, commentId);
    }

    @Override
    public int getViewType() {
        return CommentsDelegatesManager.VIEW_TYPE_EDIT_COMMENT;
    }
}
