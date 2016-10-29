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
        repliesPresenter.getRepliesList(unparceledModel.getThreadId());

        return repliesView;
    }

    /**
     * callbacks from presenters
     */

    @Override
    public void displayReplies(List<ReplyModel> repliesList) {
        this.replies = new ArrayList<>(repliesList);
        replies.add(0, unparceledModel);

        repliesDelegatesManager = new RepliesDelegatesManager(
                replyText -> replyInputPresenter.postReply(unparceledModel.getThreadId(), replyText),
                this, userIdentifier
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
    public void onDeletedComment(int position) {
        if (position == RepliesDelegatesManager.HEADER_COMMENT_POS) {
            replies.clear();
            repliesAdapter.notifyDataSetChanged();
        } else {
            replies.remove(position);
            repliesAdapter.notifyItemRemoved(position + RepliesDelegatesManager.NUMBER_OF_VIEW_ABOVE);
        }
    }

    @Override
    public void onMarkedAsSpam(int position) {
        if (position == RepliesDelegatesManager.HEADER_COMMENT_POS) {
            replies.clear();
            repliesAdapter.notifyDataSetChanged();
        } else {
            replies.remove(position);
            repliesAdapter.notifyItemRemoved(position + RepliesDelegatesManager.NUMBER_OF_VIEW_ABOVE);
        }
    }

    // TODO: check if user channel id is OK
    @Override
    public void onUpdatedReply(int position, ReplyModel replyModel) {
        replies.remove(position);
        replies.add(position, replyModel);
        repliesAdapter.notifyItemChanged(position + RepliesDelegatesManager.NUMBER_OF_VIEW_ABOVE);
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
    public void onActionReplyClick(String commentId, Pair<Integer, Integer> commentIndex) {
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
    public void onActionEditClick(String commentId, Pair<Integer, Integer> commentIndex) {
        int index = commentIndex.first == -1 ? commentIndex.second : commentIndex.first;

        UpdateReplyObj updateReplyObj = new UpdateReplyObj(commentIndex, commentId, extractText(commentIndex));

        replies.remove(index);
        replies.add(index, updateReplyObj);
        if (commentIndex.first == -1) {
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
    public void onSendEditedClick(Pair<Integer, Integer> commentIndex, String commentText, String commentId) {
        if (commentIndex.first == -1) {
            repliesPresenter.updateReply(commentId, commentText, commentIndex.second, userIdentifier);
        } else {
            repliesPresenter.updatePrimaryComment(commentId, commentText);
        }
    }

    @Override
    public void onActionDeleteClick(String commentId, Pair<Integer, Integer> commentIndex) {
        int index = commentIndex.first == -1 ? commentIndex.second : commentIndex.first;
        repliesPresenter.deleteComment(commentId, index);
    }

    @Override
    public void onActionMarkAsSpamClick(String commentId, Pair<Integer, Integer> commentIndex) {
        int index = commentIndex.first == -1 ? commentIndex.second : commentIndex.first;
        repliesPresenter.markAsSpam(commentId, index);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        repliesPresenter.destroy();
        replyInputPresenter.destroy();
    }
}
