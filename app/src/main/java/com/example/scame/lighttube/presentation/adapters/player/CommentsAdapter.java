package com.example.scame.lighttube.presentation.adapters.player;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;
import com.example.scame.lighttube.presentation.fragments.PlayerFooterFragment;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;
import com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter;

import java.util.List;

import javax.inject.Inject;

// TODO: rewrite with delegates approach

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements IReplyInputPresenter.ReplyView {

    public static final int VIEW_ABOVE_NUMBER = 2;

    private static final int HEADER_LAYOUT_POSITION = 0;
    private static final int COMMENT_INPUT_POSITION = 1;

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_THREAD_COMMENT = 1;
    private static final int VIEW_TYPE_ONE_REPLY = 2;
    private static final int VIEW_TYPE_TWO_REPLIES = 3;
    private static final int VIEW_TYPE_ALL_REPLIES = 4;
    private static final int VIEW_TYPE_COMMENT_INPUT = 5;
    private static final int VIEW_TYPE_EDIT_COMMENT = 6;

    private List<ThreadCommentModel> comments;

    private PlayerFooterFragment.PlayerFooterListener allRepliesListener;

    private String videoTitle;

    private String videoId;

    private String userIdentifier;

    private Context context;

    private PlayerFooterFragment.PostedCommentListener postedCommentListener;

    private CommentActionListener commentActionListener;

    private RecyclerView recyclerView;

    @Inject
    IReplyInputPresenter<IReplyInputPresenter.ReplyView> presenter;

    private int tempFirstIndex = -1;

    public CommentsAdapter(RecyclerView recyclerView, CommentActionListener commentActionListener,
                           PlayerFooterFragment.PlayerFooterListener allRepliesListener,
                           List<ThreadCommentModel> comments, Context context,
                           String videoTitle, String videoId, String userIdentifier,
                           PlayerFooterFragment.PostedCommentListener postedCommentListener) {

        this.postedCommentListener = postedCommentListener;
        this.allRepliesListener = allRepliesListener;
        this.userIdentifier = userIdentifier;
        this.recyclerView = recyclerView;
        this.videoTitle = videoTitle;
        this.videoId = videoId;
        this.comments = comments;
        this.context = context;
        this.commentActionListener = commentActionListener;

        inject();
        presenter.setView(this);
    }

    private void inject() {
        if (context instanceof PlayerActivity) {
            ((PlayerActivity) context).getRepliesComponent().inject(this);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case VIEW_TYPE_HEADER:
                View headerView = inflater.inflate(R.layout.player_header_item, parent, false);
                viewHolder = new HeaderViewHolder(headerView, context, videoId, videoTitle,
                        userIdentifier);
                break;
            case VIEW_TYPE_COMMENT_INPUT:
                View inputView = inflater.inflate(R.layout.comment_input_item, parent, false);
                viewHolder = new CommentInputViewHolder(postedCommentListener, inputView, context,
                        videoId, userIdentifier);
                break;
            case VIEW_TYPE_THREAD_COMMENT:
                View threadCommentView = inflater.inflate(R.layout.comment_group_item, parent, false);
                viewHolder = new ThreadCommentViewHolder(threadCommentView, userIdentifier,
                        commentActionListener, this::updateComment, this::replyToReply);
                break;
            case VIEW_TYPE_ONE_REPLY:
                View oneReplyView = inflater.inflate(R.layout.comment_group_item, parent, false);
                viewHolder = new OneReplyViewHolder(oneReplyView, userIdentifier,
                        commentActionListener, this::updateComment, this::replyToReply);
                break;
            case VIEW_TYPE_TWO_REPLIES:
                View twoRepliesView = inflater.inflate(R.layout.comment_group_item, parent, false);
                viewHolder = new TwoRepliesViewHolder(twoRepliesView, userIdentifier,
                        commentActionListener, this::updateComment, this::replyToReply);
                break;
            case VIEW_TYPE_ALL_REPLIES:
                View allRepliesView = inflater.inflate(R.layout.comment_group_item, parent, false);
                viewHolder = new AllRepliesViewHolder(allRepliesListener, commentActionListener,
                        allRepliesView, comments, userIdentifier, this::updateComment, this::replyToReply);
                break;
            case VIEW_TYPE_EDIT_COMMENT:
                View editCommentView = inflater.inflate(R.layout.comment_input_item, parent, false);
                viewHolder = new EditCommentViewHolder(editCommentView, commentActionListener);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ThreadCommentViewHolder) {
            ThreadCommentViewHolder threadCommentViewHolder = (ThreadCommentViewHolder) holder;
            threadCommentViewHolder.bindThreadCommentView(position - VIEW_ABOVE_NUMBER, comments, this::replyToReply);
        } else if (holder instanceof OneReplyViewHolder) {
            OneReplyViewHolder oneReplyViewHolder = (OneReplyViewHolder) holder;
            oneReplyViewHolder.bindOneReplyView(position - VIEW_ABOVE_NUMBER, comments, this::replyToReply);
        } else if (holder instanceof TwoRepliesViewHolder) {
            TwoRepliesViewHolder twoRepliesViewHolder = (TwoRepliesViewHolder) holder;
            twoRepliesViewHolder.bindTwoRepliesView(position - VIEW_ABOVE_NUMBER, comments, this::replyToReply);
        } else if (holder instanceof AllRepliesViewHolder) {
            AllRepliesViewHolder allRepliesViewHolder = (AllRepliesViewHolder) holder;
            allRepliesViewHolder.bindAllRepliesView(position - VIEW_ABOVE_NUMBER, comments, this::replyToReply);
        } else if (holder instanceof EditCommentViewHolder) {
            EditCommentViewHolder editCommentViewHolder = (EditCommentViewHolder) holder;

            UpdateCommentModelHolder updateCommentHolder = (UpdateCommentModelHolder) comments.get(position - VIEW_ABOVE_NUMBER);
            Pair<Integer, Integer> commentIndex = updateCommentHolder.getPairedPosition();
            String commentId = updateCommentHolder.getThreadId();
            String commentText = comments.get(commentIndex.first).getTextDisplay();

            editCommentViewHolder.bindCommentEditorHolder(commentIndex, commentText, commentId);
        }
    }

    @Override
    public void displayReply(ReplyModel replyModel) {
        if (tempFirstIndex != -1) {
            ThreadCommentModel threadCommentModel = comments.get(tempFirstIndex);
            replyModel.setAuthorChannelId(userIdentifier);
            threadCommentModel.getReplies().add(0, replyModel);
            notifyItemChanged(tempFirstIndex + VIEW_ABOVE_NUMBER);
        }
    }

    private void replyToReply(Pair<Integer, Integer> commentIndex, String commentId) {
        CommentsViewHolder commentsViewHolder = (CommentsViewHolder) recyclerView
                .findViewHolderForAdapterPosition(commentIndex.first + VIEW_ABOVE_NUMBER);

        if (commentsViewHolder != null) {
            this.tempFirstIndex = commentIndex.first;
            commentsViewHolder.activateReplyInputField(v -> {
                if (v instanceof EditText) {
                    presenter.postReply(comments.get(commentIndex.first).getThreadId(),
                            ((EditText) v).getText().toString());
                }
            }, extractTarget(commentIndex));
        }
    }

    private String extractTarget(Pair<Integer, Integer> commentIndex) {
        if (commentIndex.second == -1) {
            return comments.get(commentIndex.first).getAuthorName();
        } else {
            return comments.get(commentIndex.first).getReplies().get(commentIndex.second).getAuthorName();
        }
    }

    // will be called from view holders (through callback)
    private void updateComment(Pair<Integer, Integer> commentIndex, String commentId) {
        UpdateCommentModelHolder updateCommentHolder = new UpdateCommentModelHolder();
        updateCommentHolder.setPairedPosition(commentIndex);
        updateCommentHolder.setThreadId(commentId);

        comments.remove(+commentIndex.first);
        comments.add(commentIndex.first, updateCommentHolder);
        notifyItemChanged(commentIndex.first + VIEW_ABOVE_NUMBER);
    }

    @Override
    public int getItemCount() {
        return comments.size() + VIEW_ABOVE_NUMBER;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == HEADER_LAYOUT_POSITION) {
            return VIEW_TYPE_HEADER;
        } else if (position == COMMENT_INPUT_POSITION) {
            return VIEW_TYPE_COMMENT_INPUT;
        } else if (comments.get(position - VIEW_ABOVE_NUMBER) instanceof UpdateCommentModelHolder) {
            return VIEW_TYPE_EDIT_COMMENT;
        } else if (comments.get(position - VIEW_ABOVE_NUMBER).getReplies().size() == 0) {
            return VIEW_TYPE_THREAD_COMMENT;
        } else if (comments.get(position - VIEW_ABOVE_NUMBER).getReplies().size() == 1) {
            return VIEW_TYPE_ONE_REPLY;
        } else if (comments.get(position - VIEW_ABOVE_NUMBER).getReplies().size() == 2) {
            return VIEW_TYPE_TWO_REPLIES;
        } else {
            return VIEW_TYPE_ALL_REPLIES;
        }
    }

    /**
     * only used to identify the types of binding that must be applied */

    private static class ThreadCommentViewHolder extends CommentsViewHolder {

        ThreadCommentViewHolder(View itemView, String identifier, CommentActionListener commentActionListener,
                                EditCommentListener editCommentListener, ReplyToIndividualListener toIndividualListener) {
            super(itemView, identifier, commentActionListener, editCommentListener, toIndividualListener);
        }
    }

    private static class OneReplyViewHolder extends CommentsViewHolder {

        OneReplyViewHolder(View itemView, String identifier, CommentActionListener commentActionListener,
                           EditCommentListener editCommentListener, ReplyToIndividualListener toIndividualListener) {
            super(itemView, identifier, commentActionListener, editCommentListener, toIndividualListener);
        }
    }

    private static class TwoRepliesViewHolder extends CommentsViewHolder {

        TwoRepliesViewHolder(View itemView, String identifier, CommentActionListener commentActionListener,
                             EditCommentListener editCommentListener, ReplyToIndividualListener toIndividualListener) {
            super(itemView, identifier, commentActionListener, editCommentListener, toIndividualListener);
        }
    }

    private static class AllRepliesViewHolder extends CommentsViewHolder {

        AllRepliesViewHolder(PlayerFooterFragment.PlayerFooterListener footerListener,
                             CommentActionListener commentActionListener,
                             View itemView, List<ThreadCommentModel> comments, String identifier,
                             EditCommentListener editCommentListener, ReplyToIndividualListener toIndividualListener) {
            super(footerListener, commentActionListener, itemView, comments, identifier,
                    editCommentListener, toIndividualListener);
        }
    }
}
