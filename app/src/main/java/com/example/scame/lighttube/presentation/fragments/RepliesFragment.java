package com.example.scame.lighttube.presentation.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.adapters.player.RepliesAdapter;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.presenters.IRepliesPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepliesFragment extends Fragment implements IRepliesPresenter.RepliesView {

    @BindView(R.id.replies_fragment_rv) RecyclerView repliesRv;

    @Inject
    IRepliesPresenter<IRepliesPresenter.RepliesView> presenter;

    private String threadCommentId;

    private ReplyListModel replies;

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
        ((PlayerActivity) getActivity()).getPlayerComponent().inject(this);
        this.threadCommentId = getArguments().getString(RepliesFragment.class.getCanonicalName());

        presenter.setView(this);
        presenter.getRepliesList(threadCommentId);

        return repliesView;
    }

    @Override
    public void displayReplies(ReplyListModel replyListModel) {
        replies = replyListModel;

        RepliesAdapter repliesAdapter = new RepliesAdapter(replies, getActivity());
        repliesRv.setHasFixedSize(true);
        repliesRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        repliesRv.setAdapter(repliesAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
