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
import com.example.scame.lighttube.presentation.adapters.player.replies.UpdateReplyObj;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;
import com.example.scame.lighttube.presentation.presenters.IRepliesPresenter;
import com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter;
import com.example.scame.lighttube.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.scame.lighttube.presentation.adapters.player.replies.RepliesDelegatesManager.REPLY_INPUT_POS;
import static com.example.scame.lighttube.presentation.adapters.player.replies.RepliesDelegatesManager.VIEW_TYPE_REPLY_INPUT;

public class RepliesFragment extends Fragment implements IRepliesPresenter.RepliesView, CommentActionListener,
        IReplyInputPresenter.ReplyView {

    private static final int INSERT_REPLY_POS = 1;

    int currentPage;
    boolean isLoading;
    boolean isConnectedPreviously = true;

    @BindView(R.id.replies_fragment_rv) RecyclerView repliesRv;

    @Inject
    IRepliesPresenter<IRepliesPresenter.RepliesView> repliesPresenter;

    @Inject
    IReplyInputPresenter<IReplyInputPresenter.ReplyView> replyInputPresenter;

    private RepliesDelegatesManager repliesDelegatesManager;

    private RepliesAdapter repliesAdapter;

    private List<Object> replies;

    private ThreadCommentModel unparceledModel;

    private String userIdentifier;

    public interface RepliesListener {

        void onPostReplyClicked(String replyText);
    }

    public static RepliesFragment newInstance(ThreadCommentModel threadCommentModel, String identifier) {
        RepliesFragment repliesFragment = new RepliesFragment();

        Bundle args = new Bundle();
        args.putParcelable(RepliesFragment.class.getCanonicalName(), threadCommentModel);
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
        this.unparceledModel = getArguments().getParcelable(RepliesFragment.class.getCanonicalName());
        this.userIdentifier = getArguments().getString(getString(R.string.identifier_key));

        repliesPresenter.setView(this);
        replyInputPresenter.setView(this);
        repliesPresenter.getRepliesList(unparceledModel.getThreadId(), currentPage);

        return repliesView;
    }

    /**
     * callbacks from presenters
     */

    @Override
    public void displayReplies(List<ReplyModel> repliesList, int page) {
        if (page == 0) {
            initializeAdapter(repliesList);
        } else {
            updateScrolledList(repliesList);
        }
    }

    private void updateScrolledList(List<ReplyModel> repliesList) {
        replies.remove(replies.size() - 1);
        repliesAdapter.notifyItemRemoved(replies.size());

        replies.addAll(repliesList);
        repliesAdapter.notifyItemRangeInserted(repliesAdapter.getItemCount(), repliesList.size());

        repliesAdapter.setLoading(false);
    }

    private void initializeAdapter(List<ReplyModel> repliesList) {
        this.replies = new ArrayList<>(repliesList);
        replies.add(0, unparceledModel);

        repliesRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        constructAdapter();
        repliesRv.setHasFixedSize(true);
        repliesRv.setAdapter(repliesAdapter);
    }

    private void constructAdapter() {
        repliesDelegatesManager = new RepliesDelegatesManager(replyText ->
                replyInputPresenter.postReply(unparceledModel.getThreadId(), replyText), this, userIdentifier);
        repliesAdapter = new RepliesAdapter(repliesDelegatesManager, replies, repliesRv);

        repliesAdapter.setCurrentPage(currentPage);
        repliesAdapter.setConnectedPreviously(isConnectedPreviously);
        repliesAdapter.setLoading(isLoading);

        setupOnLoadMoreListener();
    }

    private void setupOnLoadMoreListener() {
        repliesAdapter.setOnLoadMoreListener((page) -> {
            replies.add(null);
            repliesAdapter.notifyItemInserted(replies.size() - 1);

            currentPage = page;
            repliesPresenter.getRepliesList(unparceledModel.getThreadId(), currentPage);
        });
    }


    @Override // called after a reply was posted
    public void displayReply(ReplyModel replyModel) {  // this dirty hack was made because JSON returned by Youtube Data API
        replyModel.setAuthorChannelId(userIdentifier); // doesn't contain author channel id field
                                                       // so, to avoid making an additional request we set it by hand
        hideKeyboard();
        insertPostedReply(replyModel);
    }

    @Override
    public void onDeletedComment(String deletedCommentId) {
        int index = Utility.RepliesUtil.getFilteredIndex(deletedCommentId, replies);

        if (index == RepliesDelegatesManager.HEADER_COMMENT_POS) {
            replies.clear();
            repliesAdapter.notifyDataSetChanged();
        } else {
            replies.remove(index);
            repliesAdapter.notifyItemRemoved(index + RepliesDelegatesManager.NUMBER_OF_VIEW_ABOVE);
        }
    }

    @Override
    public void onMarkedAsSpam(String markedCommentId) {
        int index = Utility.RepliesUtil.getFilteredIndex(markedCommentId, replies);

        if (index == RepliesDelegatesManager.HEADER_COMMENT_POS) {
            replies.clear();
            repliesAdapter.notifyDataSetChanged();
        } else {
            replies.remove(index);
            repliesAdapter.notifyItemRemoved(index + RepliesDelegatesManager.NUMBER_OF_VIEW_ABOVE);
        }
    }

    // TODO: check if user channel id is OK
    @Override
    public void onUpdatedReply(ReplyModel replyModel) {
        int index = Utility.RepliesUtil.getFilteredIndex(replyModel.getCommentId(), replies);

        replies.remove(index);
        replies.add(index, replyModel);
        repliesAdapter.notifyItemChanged(index + RepliesDelegatesManager.NUMBER_OF_VIEW_ABOVE);
    }

    @Override
    public void onUpdatedPrimaryComment(ThreadCommentModel threadCommentModel) {
        this.unparceledModel = threadCommentModel;
        replies.remove(RepliesDelegatesManager.HEADER_COMMENT_POS);
        replies.add(RepliesDelegatesManager.HEADER_COMMENT_POS, threadCommentModel);
        repliesAdapter.notifyItemChanged(RepliesDelegatesManager.HEADER_COMMENT_POS);
    }

    private void insertPostedReply(ReplyModel replyModel) {
        replies.add(INSERT_REPLY_POS, replyModel);
        repliesAdapter.notifyItemInserted(INSERT_REPLY_POS + 1); // take into account an input field
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    /**
     * callbacks from viewHolders
     */

    @Override
    public void onActionReplyClick(String commentId) {
        Pair<Integer, Integer> commentIndex = Utility.RepliesUtil.getReplyIndexById(commentId, replies);

        if (repliesAdapter.getItemViewType(REPLY_INPUT_POS) == VIEW_TYPE_REPLY_INPUT) {
            if (repliesRv.findViewHolderForAdapterPosition(REPLY_INPUT_POS) == null) {
                repliesDelegatesManager.setAuthorName(true, getAuthorName(commentIndex));
                repliesRv.scrollToPosition(REPLY_INPUT_POS); // will be bound soon
            } else {
                ReplyInputViewHolder replyInputViewHolder = (ReplyInputViewHolder)
                        repliesRv.findViewHolderForAdapterPosition(REPLY_INPUT_POS);
                replyInputViewHolder.giveFocus(getAuthorName(commentIndex));
            }
        }
    }

    // shouldn't be here
    private String getAuthorName(Pair<Integer, Integer> index) {
        if (index.first == -1) {
            return ((ReplyModel) replies.get(index.second)).getAuthorName();
        } else {
            return ((ThreadCommentModel) replies.get(RepliesDelegatesManager.HEADER_COMMENT_POS)).getAuthorName();
        }
    }

    @Override
    public void onActionEditClick(String commentId) {
        Pair<Integer, Integer> pairedIndex = Utility.RepliesUtil.getReplyIndexById(commentId, replies);
        int index = Utility.RepliesUtil.getFilteredIndex(commentId, replies);

        UpdateReplyObj updateReplyObj = new UpdateReplyObj(pairedIndex, commentId, extractText(pairedIndex));

        replies.remove(index);
        replies.add(index, updateReplyObj);
        if (pairedIndex.first == -1) {
            repliesAdapter.notifyItemChanged(index + RepliesDelegatesManager.NUMBER_OF_VIEW_ABOVE);
        } else {
            repliesAdapter.notifyItemChanged(index);
        }
    }

    // refactor
    private String extractText(Pair<Integer, Integer> commentIndex) {
        return commentIndex.first == -1 ? ((ReplyModel) replies.get(commentIndex.second)).getTextDisplay()
                                        : ((ThreadCommentModel) replies.get(commentIndex.first)).getTextDisplay();
    }

    @Override
    public void onSendEditedClick(String commentText, String commentId) {
        Pair<Integer, Integer> commentIndex = Utility.RepliesUtil.getReplyIndexById(commentId, replies);
        if (commentIndex.first == -1) {
            repliesPresenter.updateReply(commentId, commentText, userIdentifier);
        } else {
            repliesPresenter.updatePrimaryComment(commentId, commentText);
        }
    }

    @Override
    public void onActionDeleteClick(String commentId) {
        repliesPresenter.deleteComment(commentId);
    }

    @Override
    public void onActionMarkAsSpamClick(String commentId) {
        repliesPresenter.markAsSpam(commentId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        repliesPresenter.destroy();
        replyInputPresenter.destroy();
    }
}
