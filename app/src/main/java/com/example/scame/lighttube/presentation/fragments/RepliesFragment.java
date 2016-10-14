package com.example.scame.lighttube.presentation.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.adapters.player.RepliesAdapter;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.presenters.IRepliesPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepliesFragment extends Fragment implements IRepliesPresenter.RepliesView {

    private static final int INSERT_REPLY_POS = 0;

    @BindView(R.id.replies_fragment_rv) RecyclerView repliesRv;

    @Inject
    IRepliesPresenter<IRepliesPresenter.RepliesView> presenter;

    private RepliesAdapter repliesAdapter;

    private ReplyListModel replies;

    private String threadCommentId;

    public interface RepliesListener {

        void onPostedReply(ReplyModel replyModel);
    }

    public static RepliesFragment newInstance(String threadCommentId) {
        RepliesFragment repliesFragment = new RepliesFragment();

        Bundle args = new Bundle();
        args.putString(RepliesFragment.class.getCanonicalName(), threadCommentId);
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

        presenter.setView(this);
        presenter.getRepliesList(threadCommentId);

        return repliesView;
    }

    @Override
    public void displayReplies(ReplyListModel replyListModel) {
        replies = replyListModel;

        repliesAdapter = new RepliesAdapter(replies, getActivity(), this::onPostedReply);
        repliesRv.setHasFixedSize(true);
        repliesRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        repliesRv.setAdapter(repliesAdapter);
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
