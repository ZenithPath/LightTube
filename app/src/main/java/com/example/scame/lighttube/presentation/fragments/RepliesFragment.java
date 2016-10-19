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
import com.example.scame.lighttube.presentation.adapters.player.RepliesAdapter;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.presenters.IRepliesPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepliesFragment extends Fragment implements IRepliesPresenter.RepliesView, CommentActionListener {

    private static final int INSERT_REPLY_POS = 0;

    @BindView(R.id.replies_fragment_rv) RecyclerView repliesRv;

    @Inject
    IRepliesPresenter<IRepliesPresenter.RepliesView> presenter;

    private RepliesAdapter repliesAdapter;

    private ReplyListModel replies;

    private String threadCommentId;

    private String userIdentifier;

    public interface RepliesListener {

        void onPostedReply(ReplyModel replyModel);
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

        presenter.setView(this);
        presenter.getRepliesList(threadCommentId);

        return repliesView;
    }

    @Override
    public void displayReplies(ReplyListModel replyListModel) {
        replies = replyListModel;

        repliesAdapter = new RepliesAdapter(this, replies, getActivity(), this::onPostedReply, userIdentifier);
        repliesRv.setHasFixedSize(true);
        repliesRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        repliesRv.setAdapter(repliesAdapter);
    }

    @Override
    public void onDeletedReply(int position) {
        replies.remove(position);
        repliesAdapter.notifyItemRemoved(position + RepliesAdapter.VIEW_ABOVE_NUMBER);
    }

    @Override
    public void onMarkedAsSpam(int position) {
        replies.remove(position);
        repliesAdapter.notifyItemRemoved(position + RepliesAdapter.VIEW_ABOVE_NUMBER);
    }

    // callbacks from view holders

    @Override
    public void onDeleteClick(String commentId, Pair<Integer, Integer> commentIndex) {
        presenter.deleteReply(commentId, commentIndex.second);
    }

    @Override
    public void onUpdateClick(String commentId, Pair<Integer, Integer> commentIndex, String updatedText) {

    }

    @Override
    public void onMarkAsSpamClick(String commentId, Pair<Integer, Integer> commentIndex) {
        presenter.markAsSpam(commentId, commentIndex.second);
    }

    public void onPostedReply(ReplyModel replyModel) {
        hideKeyboard();
        insertPostedReply(replyModel);
    }

    private void insertPostedReply(ReplyModel replyModel) {
        replies.addReplyModel(INSERT_REPLY_POS, replyModel);
        repliesAdapter.notifyItemInserted(INSERT_REPLY_POS);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
