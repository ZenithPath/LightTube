package com.example.scame.lighttube.presentation.adapters.player;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditCommentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.comment_input) EditText commentInput;

    private CommentActionListener commentActionListener;

    public EditCommentViewHolder(View itemView, CommentActionListener commentActionListener) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        this.commentActionListener = commentActionListener;
    }

    public void bindCommentEditorHolder(Pair<Integer, Integer> commentIndex, String commentText, String commentId) {
        commentInput.setText(commentText);
        handleInputFieldFocus();
        commentInput.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;

            if (actionId == EditorInfo.IME_ACTION_SEND) {
                commentActionListener.onUpdateClick(commentId, commentIndex, commentInput.getText().toString());
                handled = true;
            }

            return handled;
        });
    }

    private void handleInputFieldFocus() {
        if (commentInput.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) commentInput.getContext().
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
}
