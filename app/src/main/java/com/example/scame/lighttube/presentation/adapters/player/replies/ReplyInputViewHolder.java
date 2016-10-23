package com.example.scame.lighttube.presentation.adapters.player.replies;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.fragments.RepliesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReplyInputViewHolder extends RecyclerView.ViewHolder  {

    @BindView(R.id.comment_input) EditText replyInput;

    private RepliesFragment.RepliesListener repliesListener;

    public ReplyInputViewHolder(RepliesFragment.RepliesListener repliesListener, View itemView) {
        super(itemView);
        this.repliesListener = repliesListener;
        ButterKnife.bind(this, itemView);
        setOnEditActionListener();
    }

    private void setOnEditActionListener() {
        replyInput.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;

            if (actionId == EditorInfo.IME_ACTION_SEND) {
                repliesListener.onPostReplyClicked(replyInput.getText().toString());
                replyInput.setText(""); // FIXME: should happen only after delivering
                handled = true;
            }
            return handled;
        });
    }

    public void giveFocus(String target) {
        if (replyInput.requestFocus()) {
            replyInput.setText("");
            replyInput.append("+" + target + " ");

            InputMethodManager imm = (InputMethodManager) replyInput.getContext().
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
}