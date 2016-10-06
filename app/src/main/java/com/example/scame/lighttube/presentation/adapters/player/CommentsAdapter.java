package com.example.scame.lighttube.presentation.adapters.player;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_THREAD_COMMENT = 1;
    private static final int VIEW_TYPE_ONE_REPLY = 2;
    private static final int VIEW_TYPE_TWO_REPLIES = 3;
    private static final int VIEW_TYPE_ALL_REPLIES = 4;

    private List<ThreadCommentModel> threadCommentModels;

    private String videoTitle;

    private String videoId;

    private Context context;

    public CommentsAdapter(List<ThreadCommentModel> threadCommentModels, Context context,
                           String videoTitle, String videoId) {
        this.videoTitle = videoTitle;
        this.videoId = videoId;
        this.threadCommentModels = threadCommentModels;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case VIEW_TYPE_HEADER:
                View headerView = inflater.inflate(R.layout.player_header_item, parent, false);
                viewHolder = new HeaderViewHolder(headerView, context);
                break;
            case VIEW_TYPE_THREAD_COMMENT:
                View threadCommentView = inflater.inflate(R.layout.comment_group_item, parent, false);
                viewHolder = new ThreadCommentViewHolder(threadCommentView);
                break;
            case VIEW_TYPE_ONE_REPLY:
                View oneReplyView = inflater.inflate(R.layout.comment_group_item, parent, false);
                viewHolder = new OneReplyViewHolder(oneReplyView);
                break;
            case VIEW_TYPE_TWO_REPLIES:
                View twoRepliesView = inflater.inflate(R.layout.comment_group_item, parent, false);
                viewHolder = new TwoRepliesViewHolder(twoRepliesView);
                break;
            case VIEW_TYPE_ALL_REPLIES:
                View allRepliesView = inflater.inflate(R.layout.comment_group_item, parent, false);
                viewHolder = new AllRepliesViewHolder(allRepliesView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.bindHeaderViewHolder(videoId, videoTitle);
        } else if (holder instanceof ThreadCommentViewHolder) {
            ThreadCommentViewHolder threadCommentViewHolder = (ThreadCommentViewHolder) holder;
            threadCommentViewHolder.bindThreadCommentView(position, threadCommentModels);
        } else if (holder instanceof OneReplyViewHolder) {
            OneReplyViewHolder oneReplyViewHolder = (OneReplyViewHolder) holder;
            oneReplyViewHolder.bindOneReplyView(position, threadCommentModels);
        } else if (holder instanceof TwoRepliesViewHolder) {
            TwoRepliesViewHolder twoRepliesViewHolder = (TwoRepliesViewHolder) holder;
            twoRepliesViewHolder.bindTwoRepliesView(position, threadCommentModels);
        } else if (holder instanceof AllRepliesViewHolder) {
            AllRepliesViewHolder allRepliesViewHolder = (AllRepliesViewHolder) holder;
            allRepliesViewHolder.bindAllRepliesView(position, threadCommentModels);
        }
    }

    @Override
    public int getItemCount() {
        return threadCommentModels.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else if (threadCommentModels.get(position - 1).getReplies().size() == 0) {
            return VIEW_TYPE_THREAD_COMMENT;
        } else if (threadCommentModels.get(position - 1).getReplies().size() == 1) {
            return VIEW_TYPE_ONE_REPLY;
        } else if (threadCommentModels.get(position - 1).getReplies().size() == 2) {
            return VIEW_TYPE_TWO_REPLIES;
        } else {
            return VIEW_TYPE_ALL_REPLIES;
        }
    }

    /**
     * only used to identify the types of binding that must be applied */

    private class ThreadCommentViewHolder extends CommentsViewHolder {

        ThreadCommentViewHolder(View itemView) {
            super(itemView, context);
        }
    }

    private class OneReplyViewHolder extends CommentsViewHolder {

        OneReplyViewHolder(View itemView) {
            super(itemView, context);
        }
    }

    private class TwoRepliesViewHolder extends CommentsViewHolder {

        TwoRepliesViewHolder(View itemView) {
            super(itemView, context);
        }
    }

    private class AllRepliesViewHolder extends CommentsViewHolder {

        AllRepliesViewHolder(View itemView) {
            super(itemView, context);
        }
    }
}
