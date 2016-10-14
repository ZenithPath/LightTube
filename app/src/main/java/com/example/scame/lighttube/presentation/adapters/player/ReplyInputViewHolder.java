package com.example.scame.lighttube.presentation.adapters.player;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.fragments.RepliesFragment;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReplyInputViewHolder extends RecyclerView.ViewHolder implements IReplyInputPresenter.ReplyView {

    @Inject
    IReplyInputPresenter<IReplyInputPresenter.ReplyView> presenter;

    @BindView(R.id.comment_input) EditText replyInput;

    private RepliesFragment.RepliesListener repliesListener;

    private String identifier;

    public ReplyInputViewHolder(RepliesFragment.RepliesListener repliesListener, View itemView,
                                Context context, String parentId, String identifier) {
        super(itemView);

        this.identifier = identifier;
        this.repliesListener = repliesListener;
        ButterKnife.bind(this, itemView);
        inject(context);
        setOnEditActionListener(parentId);
    }

    private void inject(Context context) {
        if (context instanceof PlayerActivity) {
            ((PlayerActivity) context).getRepliesComponent().inject(this);
        }
    }

    private void setOnEditActionListener(String parentId) {
        replyInput.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;

            if (actionId == EditorInfo.IME_ACTION_SEND) {
                presenter.setView(this);
                presenter.postReply(parentId, replyInput.getText().toString());
                handled = true;
            }

            return handled;
        });
    }

    @Override
    public void displayReply(ReplyModel replyModel) {
        if (repliesListener != null) {                  // this dirty hack was made because JSON returned by Youtube Data API
            replyModel.setAuthorChannelId(identifier);  // doesn't contain author channel id field
                                                        // so to avoid making an additional request we set it by hand
            replyInput.setText("");
            repliesListener.onPostedReply(replyModel);
        }
    }
}