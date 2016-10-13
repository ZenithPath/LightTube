package com.example.scame.lighttube.presentation.fragments;

import android.app.Activity;
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
import com.example.scame.lighttube.presentation.adapters.player.CommentsAdapter;
import com.example.scame.lighttube.presentation.adapters.player.DividerItemDecoration;
import com.example.scame.lighttube.presentation.model.CommentListModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;
import com.example.scame.lighttube.presentation.presenters.IPlayerFooterPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerFooterFragment extends Fragment implements IPlayerFooterPresenter.CommentsView {

    private static final int INSERT_COMMENT_POS = 0;

    @Inject
    IPlayerFooterPresenter<IPlayerFooterPresenter.CommentsView> presenter;

    @BindView(R.id.player_footer_rv)
    RecyclerView footerRv;

    private PlayerFooterListener footerListener;

    private CommentsAdapter commentsAdapter;

    private CommentListModel commentListModel;

    private String videoId;

    public interface PlayerFooterListener {

        void onRepliesClick(String threadCommentId);
    }

    public interface CommentInputListener {

        void onCommentPosted(ThreadCommentModel threadCommentModel);
    }

    public static PlayerFooterFragment newInstance(String videoId) {
        PlayerFooterFragment fragment = new PlayerFooterFragment();

        Bundle args = new Bundle();
        args.putString(PlayerFooterFragment.class.getCanonicalName(), videoId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof PlayerFooterListener) {
            footerListener = (PlayerFooterListener) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.player_footer_fragment, container, false);

        videoId = getArguments().getString(PlayerFooterFragment.class.getCanonicalName());
        ((PlayerActivity) getActivity()).getPlayerComponent().inject(this);
        ButterKnife.bind(this, fragmentView);

        supplyComments();

        return fragmentView;
    }

    private void supplyComments() {
        if (commentListModel == null) {
            presenter.setView(this);
            presenter.getCommentList(videoId);
        } else {
            displayComments(commentListModel);
        }
    }

    @Override
    public void displayComments(CommentListModel commentsList) {
        this.commentListModel = commentsList;
        commentsAdapter = new CommentsAdapter(footerListener, commentListModel.getThreadComments(),
                this::displayPostedComment, getActivity(), "Some title", videoId);

        footerRv.setAdapter(commentsAdapter);
        footerRv.addItemDecoration(new DividerItemDecoration(getActivity()));
        footerRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void displayPostedComment(ThreadCommentModel threadComment) {
        hideKeyboard();
        insertPostedComment(threadComment);
    }


    private void insertPostedComment(ThreadCommentModel threadComment) {
        commentListModel.addThreadCommentModel(INSERT_COMMENT_POS, threadComment);
        commentsAdapter.notifyItemInserted(INSERT_COMMENT_POS);
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
