package com.example.scame.lighttube.presentation.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.LightTubeApp;
import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.adapters.player.replies.RepliesAdapter;
import com.example.scame.lighttube.presentation.adapters.player.replies.RepliesDelegatesManager;
import com.example.scame.lighttube.presentation.adapters.player.replies.ReplyInputViewHolder;
import com.example.scame.lighttube.presentation.adapters.player.replies.UpdateReplyModelHolder;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.presenters.IRepliesPresenter;
import com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.scame.lighttube.presentation.adapters.player.replies.RepliesDelegatesManager.REPLY_INPUT_POS;
import static com.example.scame.lighttube.presentation.adapters.player.replies.RepliesDelegatesManager.VIEW_TYPE_REPLY_INPUT;

public class RepliesFragment extends Fragment implements IRepliesPresenter.RepliesView, CommentActionListener,
        IReplyInputPresenter.ReplyView {

    private static final int INSERT_REPLY_POS = 0;

    @BindView(R.id.replies_fragment_rv) RecyclerView repliesRv;

    @Inject
    IRepliesPresenter<IRepliesPresenter.RepliesView> repliesPresenter;

    @Inject
    IReplyInputPresenter<IReplyInputPresenter.ReplyView> replyInputPresenter;

    private RepliesDelegatesManager repliesDelegatesManager;

    private RepliesAdapter repliesAdapter;

    private ReplyListModel replies;

    private String threadCommentId;

    private String userIdentifier;

    public interface RepliesListener {

        void onPostReplyClicked(String replyText);
    }

    public static RepliesFragment newInstance(String threadCommentId, String identifier) {
        RepliesFragment repliesFragment = new RepliesFragment();

        Bundle args = new Bundle();
        args.putString(RepliesFragment.class.getCanonicalName(), threadCommentId);
        args.putString(LightTubeApp.getAppComponent().getContext().getString(R.string.identifier_key), identifier);
        repliesFragment.setArguments(args);

        return repliesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View repliesView = inflater.inflate(R.layout.replies_fragment, container, false);

        ButterKnife.bind(this, repliesView);
        ((PlayerActivity) getActivity()).getRepliesComponent().inject(this);
        this.threadCommentId = getArguments().getString(RepliesFragment.class.getCanonicalName());
        this.userIdentifier = getArguments().getString(getString(R.string.identifier_key));

        repliesPresenter.setView(this);
        replyInputPresenter.setView(this);
        repliesPresenter.getRepliesList(threadCommentId);

        return repliesView;
    }

    /**
     * callbacks from presenters
     */

    @Override
    public void displayReplies(ReplyListModel replyListModel) {
        replies = replyListModel;
        repliesDelegatesManager = new RepliesDelegatesManager(
                replyText -> replyInputPresenter.postReply(threadCommentId, replyText), this, userIdentifier
        );
        repliesAdapter = new RepliesAdapter(repliesDelegatesManager, replies);

        repliesRv.setHasFixedSize(true);
        repliesRv.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        repliesRv.setAdapter(repliesAdapter);
    }


    @Override // called after a reply was posted
    public void displayReply(ReplyModel replyModel) {  // this dirty hack was made because JSON returned by Youtube Data API
        replyModel.setAuthorChannelId(userIdentifier); // doesn't contain author channel id field
                                                       // so, to avoid making an additional request we set it by hand
        hideKeyboard();
        insertPostedReply(replyModel);
    }

    @Override
    public void onDeletedReply(int position) {
        replies.remove(position);
        repliesAdapter.notifyItemRemoved(position + RepliesDelegatesManager.NUMBER_OF_VIEW_ABOVE);
    }

    @Override
    public void onMarkedAsSpam(int position) {
        replies.remove(position);
        repliesAdapter.notifyItemRemoved(position + RepliesDelegatesManager.NUMBER_OF_VIEW_ABOVE);
    }

    // TODO: check if user channel id is OK
    @Override
    public void onUpdatedReply(int position, ReplyModel replyModel) {
        replies.remove(position);
        replies.addReplyModel(position, replyModel);
        repliesAdapter.notifyItemChanged(position + RepliesDelegatesManager.NUMBER_OF_VIEW_ABOVE);
    }

    private void insertPostedReply(ReplyModel replyModel) {
        replies.addReplyModel(INSERT_REPLY_POS, replyModel);
        repliesAdapter.notifyItemInserted(INSERT_REPLY_POS);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    /**
     * callbacks from viewHolders
     */

    @Override
    public void onActionReplyClick(String commentId, Pair<Integer, Integer> commentIndex) {
        if (repliesAdapter.getItemViewType(REPLY_INPUT_POS) == VIEW_TYPE_REPLY_INPUT) {
            if (repliesRv.findViewHolderForAdapterPosition(REPLY_INPUT_POS) == null) {
                repliesDelegatesManager.setModeFields(true, commentIndex.second);
                repliesRv.scrollToPosition(REPLY_INPUT_POS); // will be bound soon
            } else {
                ReplyInputViewHolder replyInputViewHolder = (ReplyInputViewHolder)
                        repliesRv.findViewHolderForAdapterPosition(REPLY_INPUT_POS);
                replyInputViewHolder.giveFocus(replies.getReplyModel(commentIndex.second).getAuthorName());
            }
        }
    }

    @Override
    public void onActionEditClick(String commentId, Pair<Integer, Integer> commentIndex) {
        UpdateReplyModelHolder updateReplyModelHolder = new UpdateReplyModelHolder();
        updateReplyModelHolder.setPosition(commentIndex.second);
        updateReplyModelHolder.setCommentId(commentId);

        replies.remove(commentIndex.second);
        replies.addReplyModel(commentIndex.second, updateReplyModelHolder);
        repliesAdapter.notifyItemChanged(commentIndex.second + RepliesDelegatesManager.NUMBER_OF_VIEW_ABOVE);
    }

    @Override
    public void onSendEditedClick(Pair<Integer, Integer> commentIndex, String commentText, String commentId) {
        repliesPresenter.updateReply(commentId, commentText, commentIndex.second, userIdentifier);
    }

    @Override
    public void onActionDeleteClick(String commentId, Pair<Integer, Integer> commentIndex) {
        repliesPresenter.deleteReply(commentId, commentIndex.second);
    }

    @Override
    public void onActionUpdateClick(String commentId, Pair<Integer, Integer> commentIndex, String updatedText) {
        repliesPresenter.updateReply(commentId, updatedText, commentIndex.second, userIdentifier);
    }

    @Override
    public void onActionMarkAsSpamClick(String commentId, Pair<Integer, Integer> commentIndex) {
        repliesPresenter.markAsSpam(commentId, commentIndex.second);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        repliesPresenter.destroy();
        replyInputPresenter.destroy();
    }
}
