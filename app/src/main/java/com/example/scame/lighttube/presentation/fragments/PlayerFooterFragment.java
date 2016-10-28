package com.example.scame.lighttube.presentation.fragments;

import android.app.Activity;
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
import android.widget.EditText;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.adapters.player.DividerItemDecoration;
import com.example.scame.lighttube.presentation.adapters.player.threads.CommentsAdapter;
import com.example.scame.lighttube.presentation.adapters.player.threads.CommentsDelegatesManager;
import com.example.scame.lighttube.presentation.adapters.player.threads.CommentsViewHolder;
import com.example.scame.lighttube.presentation.adapters.player.threads.UpdateCommentModelHolder;
import com.example.scame.lighttube.presentation.model.CommentListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;
import com.example.scame.lighttube.presentation.presenters.IPlayerFooterPresenter;
import com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.State;

// TODO: add thread replies editing

public class PlayerFooterFragment extends Fragment implements IPlayerFooterPresenter.FooterView,
        CommentActionListener, IReplyInputPresenter.ReplyView {

    private static final int INSERT_COMMENT_POS = 0;

    @Inject
    IPlayerFooterPresenter<IPlayerFooterPresenter.FooterView> footerPresenter;

    @Inject
    IReplyInputPresenter<IReplyInputPresenter.ReplyView> replyInputPresenter;

    @BindView(R.id.player_footer_rv)
    RecyclerView footerRv;

    private PlayerFooterListener footerListener;

    private CommentsAdapter commentsAdapter;

    private CommentListModel commentListModel;

    private String videoId;

    private String userIdentifier;

    // FIXME: it's more a workaround than a normal solution, flow should be changed
    @State int updatedCommentPosition = -1;

    public interface PlayerFooterListener {

        void onRepliesClick(ThreadCommentModel threadCommentModel, String identifier);
    }

    public interface PostedCommentListener {
        void onPostCommentClick(String commentText);
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
        ((PlayerActivity) getActivity()).getPlayerFooterComponent().inject(this);
        ButterKnife.bind(this, fragmentView);

        footerPresenter.setView(this);
        replyInputPresenter.setView(this);

        supplyComments();

        return fragmentView;
    }

    private void supplyComments() {
        if (commentListModel == null) {
            footerPresenter.setView(this);
            footerPresenter.getCommentList(videoId);
        } else {
            displayComments(commentListModel, userIdentifier);
        }
    }

    /**
     * callbacks from presenters
     */

    @Override
    public void displayComments(CommentListModel commentsList, String userIdentifier) {
        this.commentListModel = commentsList;
        this.userIdentifier = userIdentifier;

        CommentsDelegatesManager delegatesManager = new CommentsDelegatesManager(this, getActivity(),
                userIdentifier, videoId, footerListener,
                commentText -> footerPresenter.postComment(commentText, videoId));

        commentsAdapter = new CommentsAdapter(delegatesManager, commentListModel.getThreadComments());

        footerRv.setAdapter(commentsAdapter);
        footerRv.addItemDecoration(new DividerItemDecoration(getActivity()));
        footerRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void displayReply(ReplyModel replyModel) {
        if (updatedCommentPosition != -1) {
            ThreadCommentModel threadCommentModel = commentListModel.getThreadComments().get(updatedCommentPosition);
            replyModel.setAuthorChannelId(userIdentifier);
            threadCommentModel.getReplies().add(0, replyModel);
            commentsAdapter.notifyItemChanged(updatedCommentPosition + CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE);
        }
    }

    @Override
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
    public void onReplyUpdated(Pair<Integer, Integer> replyIndex, ReplyModel replyModel) {
        UpdateCommentModelHolder modelHolder = (UpdateCommentModelHolder) commentListModel
                .getThreadComments().remove(+replyIndex.first);

        ThreadCommentModel updatedThreadModel = new ThreadCommentModel(modelHolder);
        replyModel.setAuthorChannelId(userIdentifier);
        updatedThreadModel.setReply(replyIndex.second, replyModel);

        commentListModel.getThreadComments().add(replyIndex.first, updatedThreadModel);
        commentsAdapter.notifyItemChanged(replyIndex.first + CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE);
    }

    @Override
    public void onCommentUpdated(Pair<Integer, Integer> commentIndex, ThreadCommentModel threadCommentModel) {
        commentListModel.getThreadComments().remove(+commentIndex.first);
        commentListModel.getThreadComments().add(commentIndex.first, threadCommentModel);
        commentsAdapter.notifyItemChanged(commentIndex.first + CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE);
    }

    @Override
    public void onMarkedAsSpam(Pair<Integer, Integer> commentIndex) {
        commentListModel.deleteByPairIndex(commentIndex);
        notifyOnDelete(commentIndex);
    }

    @Override
    public void onCommentDeleted(Pair<Integer, Integer> commentIndex) {
        commentListModel.deleteByPairIndex(commentIndex);
        notifyOnDelete(commentIndex);
    }

    private void notifyOnDelete(Pair<Integer, Integer> commentIndex) {
        int firstAdapterIndex = commentIndex.first + CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE;
        Pair<Integer, Integer> adapterPairIndex = new Pair<>(firstAdapterIndex, commentIndex.second);

        if (adapterPairIndex.second == -1) {
            commentsAdapter.notifyItemRemoved(adapterPairIndex.first);
        } else {
            commentsAdapter.notifyItemChanged(adapterPairIndex.first);
        }
    }

    /**
     * callbacks from view holders, get activated when a popup option is clicked
     */

    @Override
    public void onActionReplyClick(String commentId, Pair<Integer, Integer> commentIndex) {
        CommentsViewHolder commentsViewHolder = (CommentsViewHolder) footerRv
                .findViewHolderForAdapterPosition(commentIndex.first + CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE);

        if (commentsViewHolder != null) {
            this.updatedCommentPosition = commentIndex.first;

            commentsViewHolder.giveFocusToInputField(v -> {
                if (v instanceof EditText) {
                    String threadId = commentListModel.getThreadComments().get(commentIndex.first).getThreadId();
                    replyInputPresenter.postReply(threadId, ((EditText) v).getText().toString());
                }
            }, extractTarget(commentIndex));
        }
    }

    private String extractTarget(Pair<Integer, Integer> commentIndex) {
        ThreadCommentModel threadComment = commentListModel.getThreadComments().get(commentIndex.first);

        return (commentIndex.second == -1) ? threadComment.getAuthorName()
                : threadComment.getReplies().get(commentIndex.second).getAuthorName();
    }

    @Override
    public void onActionEditClick(String commentId, Pair<Integer, Integer> commentIndex) {
        ThreadCommentModel threadCommentModel = commentListModel.getThreadComments().get(commentIndex.first);
        UpdateCommentModelHolder updateCommentHolder = new UpdateCommentModelHolder(threadCommentModel);
        updateCommentHolder.setPairedPosition(commentIndex);

        // FIXME: it's not cool that while editing all parts of a thread aren't visible
        commentListModel.getThreadComments().remove(+commentIndex.first);
        commentListModel.getThreadComments().add(commentIndex.first, updateCommentHolder);
        commentsAdapter.notifyItemChanged(commentIndex.first + CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE);
    }

    @Override
    public void onSendEditedClick(Pair<Integer, Integer> commentIndex, String commentText, String commentId) {
        if (commentIndex.second == -1) {
            footerPresenter.updateComment(commentId, commentIndex, commentText);
        } else {
            footerPresenter.updateReply(commentId, commentIndex, commentText);
        }
    }

    @Override
    public void onActionDeleteClick(String commentId, Pair<Integer, Integer> commentIndex) {
        footerPresenter.deleteThreadComment(commentId, commentIndex);
    }

    @Override
    public void onActionMarkAsSpamClick(String commentId, Pair<Integer, Integer> commentIndex) {
        footerPresenter.markAsSpam(commentId, commentIndex);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        footerPresenter.destroy();
        replyInputPresenter.destroy();
    }
}
