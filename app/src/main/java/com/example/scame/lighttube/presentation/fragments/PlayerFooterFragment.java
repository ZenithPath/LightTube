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
import com.example.scame.lighttube.presentation.adapters.player.threads.UpdateCommentObj;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;
import com.example.scame.lighttube.presentation.presenters.IPlayerFooterPresenter;
import com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter;
import com.example.scame.lighttube.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private List<Object> modelsList;

    private String videoId;

    private String userIdentifier;

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
        if (modelsList == null) {
            footerPresenter.setView(this);
            footerPresenter.getCommentList(videoId);
        } else {
            displayComments(modelsList, userIdentifier);
        }
    }

    /**
     * callbacks from presenters
     */

    @Override
    public void displayComments(List<?> commentsList, String userIdentifier) {
        this.modelsList = new ArrayList<>(commentsList);
        this.userIdentifier = userIdentifier;

        CommentsDelegatesManager delegatesManager = new CommentsDelegatesManager(this, getActivity(),
                userIdentifier, videoId, footerListener,
                commentText -> footerPresenter.postComment(commentText, videoId));

        commentsAdapter = new CommentsAdapter(delegatesManager, modelsList);

        footerRv.setAdapter(commentsAdapter);
        footerRv.addItemDecoration(new DividerItemDecoration(getActivity()));
        footerRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void displayReply(ReplyModel replyModel) {
        int index = Utility.FooterUtil.getFilteredIndex(replyModel.getParentId(), modelsList);

        ThreadCommentModel threadCommentModel = (ThreadCommentModel) modelsList.get(index);
        replyModel.setAuthorChannelId(userIdentifier);
        threadCommentModel.getReplies().add(0, replyModel);
        threadCommentModel.setReplyCount(threadCommentModel.getReplyCount() + 1);
        commentsAdapter.notifyItemChanged(index + CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE);
    }

    @Override
    public void displayPostedComment(ThreadCommentModel threadComment) {
        hideKeyboard();
        insertPostedComment(threadComment);
    }

    private void insertPostedComment(ThreadCommentModel threadComment) {
        modelsList.add(INSERT_COMMENT_POS, threadComment);
        commentsAdapter.notifyItemInserted(INSERT_COMMENT_POS);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public void onReplyUpdated(ReplyModel replyModel) {
        String replyId = replyModel.getCommentId();
        Pair<Integer, Integer> index = Utility.FooterUtil.getCommentIndexById(replyId, modelsList);

        UpdateCommentObj modelHolder = (UpdateCommentObj) modelsList.remove((int) index.first);

        ThreadCommentModel updatedThreadModel = new ThreadCommentModel(modelHolder.getThreadCommentModel());
        replyModel.setAuthorChannelId(userIdentifier);
        updatedThreadModel.setReply(index.second, replyModel);

        modelsList.add(index.first, updatedThreadModel);
        commentsAdapter.notifyItemChanged(index.first + CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE);
    }

    @Override
    public void onCommentUpdated(ThreadCommentModel threadCommentModel) {
        String threadId = threadCommentModel.getThreadId();
        Pair<Integer, Integer> commentIndex = Utility.FooterUtil.getCommentIndexById(threadId, modelsList);
        modelsList.remove((int) commentIndex.first);
        modelsList.add(commentIndex.first, threadCommentModel);
        commentsAdapter.notifyItemChanged(commentIndex.first + CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE);
    }

    @Override
    public void onMarkedAsSpam(String markedCommentId) {
        Pair<Integer, Integer> index = Utility.FooterUtil.deleteById(modelsList, markedCommentId);
        notifyOnDelete(index);
    }

    @Override
    public void onCommentDeleted(String deletedCommentId) {
        Pair<Integer, Integer> index = Utility.FooterUtil.deleteById(modelsList, deletedCommentId);
        notifyOnDelete(index);
    }

    private void notifyOnDelete(Pair<Integer, Integer> commentIndex) {
        int firstAdapterIndex = commentIndex.first + CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE;
        Pair<Integer, Integer> adapterPairIndex = new Pair<>(firstAdapterIndex, commentIndex.second);

        if (adapterPairIndex.second == -1) {
            commentsAdapter.notifyItemRemoved(adapterPairIndex.first);
        } else {
            decrementRepliesCounter(commentIndex.first);
            commentsAdapter.notifyItemChanged(adapterPairIndex.first);
        }
    }

    private void decrementRepliesCounter(int position) {
        ThreadCommentModel model = (ThreadCommentModel) modelsList.get(position);
        model.setReplyCount(model.getReplyCount() - 1);
    }

    /**
     * callbacks from view holders, get activated when a popup option is clicked
     */

    @Override
    public void onActionReplyClick(String commentId) {
        Pair<Integer, Integer> commentIndex = Utility.FooterUtil.getCommentIndexById(commentId, modelsList);

        CommentsViewHolder commentsViewHolder = (CommentsViewHolder) footerRv
                .findViewHolderForAdapterPosition(commentIndex.first + CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE);

        if (commentsViewHolder != null) {
            commentsViewHolder.giveFocusToInputField(v -> {
                if (v instanceof EditText) {
                    String threadId = ((ThreadCommentModel) modelsList.get(commentIndex.first)).getThreadId();
                    replyInputPresenter.postReply(threadId, ((EditText) v).getText().toString());
                }
            }, extractTarget(commentIndex));
        }
    }

    private String extractTarget(Pair<Integer, Integer> commentIndex) {
        ThreadCommentModel threadComment = (ThreadCommentModel) modelsList.get(commentIndex.first);

        return (commentIndex.second == -1) ? threadComment.getAuthorName()
                : threadComment.getReplies().get(commentIndex.second).getAuthorName();
    }

    @Override
    public void onActionEditClick(String commentId) {
        Pair<Integer, Integer> commentIndex = Utility.FooterUtil.getCommentIndexById(commentId, modelsList);

        ThreadCommentModel threadCommentModel = (ThreadCommentModel) modelsList.get(commentIndex.first);
        UpdateCommentObj updateCommentHolder = new UpdateCommentObj(threadCommentModel);
        updateCommentHolder.setPairedPosition(commentIndex);

        // FIXME: it's not cool that while editing all parts of a thread aren't visible
        modelsList.remove((int) commentIndex.first);
        modelsList.add(commentIndex.first, updateCommentHolder);
        commentsAdapter.notifyItemChanged(commentIndex.first + CommentsDelegatesManager.NUMBER_OF_VIEW_ABOVE);
    }

    @Override
    public void onSendEditedClick(String commentText, String commentId) {
        Pair<Integer, Integer> commentIndex = Utility.FooterUtil.getCommentIndexById(commentId, modelsList);
        if (commentIndex.second == -1) {
            footerPresenter.updateComment(commentId, commentText);
        } else {
            footerPresenter.updateReply(commentId, commentText);
        }
    }

    @Override
    public void onActionDeleteClick(String commentId) {
        footerPresenter.deleteThreadComment(commentId);
    }

    @Override
    public void onActionMarkAsSpamClick(String commentId) {
        footerPresenter.markAsSpam(commentId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        footerPresenter.destroy();
        replyInputPresenter.destroy();
    }
}
