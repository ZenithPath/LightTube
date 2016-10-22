package com.example.scame.lighttube.presentation.adapters.player;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;
import com.example.scame.lighttube.presentation.fragments.RepliesFragment;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;


public class RepliesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_ABOVE_NUMBER = 1;

    private static final int REPLY_INPUT_POS = 0;

    private static final int VIEW_TYPE_REPLY_COMMENT = 0;
    private static final int VIEW_TYPE_REPLY_INPUT = 1;
    private static final int VIEW_TYPE_EDIT_REPLY = 2;

    private RepliesFragment.RepliesListener repliesListener;

    private CommentActionListener commentActionListener;

    private ReplyListModel replies;

    private RecyclerView recyclerView;

    private String userIdentifier;

    private Context context;

    private boolean asReply;   // these fields are used to identify
    private int replyPosition; // if a reply mode should be started after bounding

    public RepliesAdapter(RecyclerView recyclerView ,CommentActionListener commentActionListener,
                          ReplyListModel replies, Context context, String userIdentifier,
                          RepliesFragment.RepliesListener repliesListener) {
        this.replies = replies;
        this.context = context;
        this.recyclerView = recyclerView;
        this.userIdentifier = userIdentifier;
        this.repliesListener = repliesListener;
        this.commentActionListener = commentActionListener;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_REPLY_INPUT) {
            View replyInputView = inflater.inflate(R.layout.comment_input_item, parent, false);
            String parentId = replies.getReplyModel(0).getParentId();
            viewHolder = new ReplyInputViewHolder(repliesListener, replyInputView,
                    context, parentId, userIdentifier);
        } else if (viewType == VIEW_TYPE_REPLY_COMMENT) {
            View replyView = inflater.inflate(R.layout.comment_item, parent, false);
            viewHolder = new RepliesViewHolder(commentActionListener, replyView, userIdentifier,
                    this::updateReply, this::replyToReply);
        } else if (viewType == VIEW_TYPE_EDIT_REPLY) {
            View editReplyView = inflater.inflate(R.layout.comment_input_item, parent, false);
            viewHolder = new EditCommentViewHolder(editReplyView, commentActionListener);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RepliesViewHolder) {
            RepliesViewHolder viewHolder = (RepliesViewHolder) holder;
            viewHolder.bindRepliesView(position - VIEW_ABOVE_NUMBER, replies);
        } else if (holder instanceof EditCommentViewHolder) {
            EditCommentViewHolder editCommentViewHolder = (EditCommentViewHolder) holder;

            Pair<Integer, Integer> replyIndex = new Pair<>(-1, position - VIEW_ABOVE_NUMBER);
            ReplyModel replyModel = replies.getReplyModel(position - VIEW_ABOVE_NUMBER);

            editCommentViewHolder.bindCommentEditorHolder(replyIndex, replyModel.getTextDisplay(), replyModel.getCommentId());
        } else if (holder instanceof ReplyInputViewHolder && asReply) {
            asReply = false;
            replyPosition = -1;
            ReplyInputViewHolder replyInputViewHolder = (ReplyInputViewHolder) holder;
            replyInputViewHolder.makeActive(replies.getReplyModel(replyPosition).getAuthorName());
        }
    }

    private void replyToReply(Pair<Integer, Integer> commentIndex, String commentId) {
        if (getItemViewType(REPLY_INPUT_POS) == VIEW_TYPE_REPLY_INPUT) {
            if (recyclerView.findViewHolderForAdapterPosition(REPLY_INPUT_POS) == null) {
                asReply = true;
                replyPosition = commentIndex.second;
                recyclerView.scrollToPosition(REPLY_INPUT_POS); // will be bound soon
            } else {
                ReplyInputViewHolder replyInputViewHolder = (ReplyInputViewHolder)
                        recyclerView.findViewHolderForAdapterPosition(REPLY_INPUT_POS);
                replyInputViewHolder.makeActive(replies.getReplyModel(commentIndex.second).getAuthorName());
            }
        }
    }

    private void updateReply(Pair<Integer, Integer> commentIndex, String commentId) {
        UpdateReplyModelHolder updateReplyModelHolder = new UpdateReplyModelHolder();
        updateReplyModelHolder.setPosition(commentIndex.second);
        updateReplyModelHolder.setCommentId(commentId);

        replies.remove(commentIndex.second);
        replies.addReplyModel(commentIndex.second, updateReplyModelHolder);
        notifyItemChanged(commentIndex.second + VIEW_ABOVE_NUMBER);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == REPLY_INPUT_POS) {
            return VIEW_TYPE_REPLY_INPUT;
        } else if (replies.getReplyModel(position - VIEW_ABOVE_NUMBER) instanceof UpdateReplyModelHolder) {
            return VIEW_TYPE_EDIT_REPLY;
        } else {
            return VIEW_TYPE_REPLY_COMMENT;
        }
    }

    @Override
    public int getItemCount() {
        return replies.getReplyModels().size() + VIEW_ABOVE_NUMBER;
    }
}
