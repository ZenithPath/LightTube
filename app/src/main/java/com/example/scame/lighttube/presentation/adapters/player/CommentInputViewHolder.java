package com.example.scame.lighttube.presentation.adapters.player;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;
import com.example.scame.lighttube.presentation.presenters.ICommentInputPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentInputViewHolder extends RecyclerView.ViewHolder implements ICommentInputPresenter.CommentInputView {

    @Inject
    ICommentInputPresenter<ICommentInputPresenter.CommentInputView> presenter;

    @BindView(R.id.comment_input) EditText commentInput;

    private CommentActionListener commentActionListener;


    public CommentInputViewHolder(CommentActionListener commentActionListener,
                                  View itemView, Context context, String videoId, String identifier) {
        super(itemView);

        this.commentActionListener = commentActionListener;
        ButterKnife.bind(this, itemView);
        inject(context);
        setOnEditorActionListener(videoId);
    }


    private void inject(Context context) {
        if (context instanceof PlayerActivity) {
            ((PlayerActivity) context).getCommentsComponent().inject(this);
        }
    }

    private void setOnEditorActionListener(String videoId) {
        commentInput.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;

            if (actionId == EditorInfo.IME_ACTION_SEND) {
                presenter.setView(this);
                presenter.postComment(commentInput.getText().toString(), videoId);
                handled = true;
            }

            return handled;
        });
    }

    @Override
    public void displayPostedComment(ThreadCommentModel threadComment) {
        if (commentActionListener != null) {
            commentInput.setText("");
            commentActionListener.onPostedComment(threadComment);
        }
    }
}
