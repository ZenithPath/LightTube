package com.example.scame.lighttube.presentation.adapters.player;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.fragments.PlayerFooterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentInputViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.comment_input) EditText commentInput;

    public CommentInputViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bindInputView(PlayerFooterFragment.CommentInputListener inputListener) {
        commentInput.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;

            if (actionId == EditorInfo.IME_ACTION_SEND) {
                inputListener.onSendCommentClick(commentInput.getText().toString());
                handled = true;
            }

            return handled;
        });
    }
}
