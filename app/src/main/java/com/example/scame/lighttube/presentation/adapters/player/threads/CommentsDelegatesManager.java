package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegatesManager;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;
import com.example.scame.lighttube.presentation.fragments.PlayerFooterFragment;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.ArrayList;
import java.util.List;

public class CommentsDelegatesManager implements AdapterDelegatesManager<List<ThreadCommentModel>> {

    public static final int NUMBER_OF_VIEW_ABOVE = 2;

    static final int VIEW_TYPE_HEADER = 0;
    static final int VIEW_TYPE_THREAD_COMMENT = 1;
    static final int VIEW_TYPE_ONE_REPLY = 2;
    static final int VIEW_TYPE_TWO_REPLIES = 3;
    static final int VIEW_TYPE_ALL_REPLIES = 4;
    static final int VIEW_TYPE_COMMENT_INPUT = 5;
    static final int VIEW_TYPE_EDIT_COMMENT = 6;

    private static final int HEADER_LAYOUT_POSITION = 0;
    private static final int COMMENT_INPUT_POSITION = 1;

    private List<AdapterDelegate<List<ThreadCommentModel>>> delegates;

    public CommentsDelegatesManager(CommentActionListener actionListener, Context context,
                                    String userIdentifier, String videoId,
                                    PlayerFooterFragment.PlayerFooterListener footerListener,
                                    PlayerFooterFragment.PostedCommentListener postedCommentListener) {
        delegates = new ArrayList<>();

        delegates.add(new CommentInputDelegate(postedCommentListener));
        delegates.add(new ThreadCommentDelegate(actionListener, userIdentifier));
        delegates.add(new SingleReplyDelegate(actionListener, userIdentifier));
        delegates.add(new CoupleRepliesDelegate(actionListener, userIdentifier));
        delegates.add(new MultipleRepliesDelegate(footerListener, actionListener, userIdentifier));
        delegates.add(new HeaderDelegate(context, videoId, null));
        delegates.add(new EditCommentDelegate(actionListener));
    }

    @Override
    public AdapterDelegatesManager<List<ThreadCommentModel>> addDelegate(@NonNull AdapterDelegate<List<ThreadCommentModel>> delegate) {
        delegates.add(delegate);
        return this;
    }

    @Override
    public int getItemViewType(@NonNull List<ThreadCommentModel> items, int position) {
        if (position == HEADER_LAYOUT_POSITION) {
            return VIEW_TYPE_HEADER;
        } else if (position == COMMENT_INPUT_POSITION) {
            return VIEW_TYPE_COMMENT_INPUT;
        } else if (items.get(position - NUMBER_OF_VIEW_ABOVE) instanceof UpdateCommentModelHolder) {
            return VIEW_TYPE_EDIT_COMMENT;
        } else if (items.get(position - NUMBER_OF_VIEW_ABOVE).getReplies().size() == 0) {
            return VIEW_TYPE_THREAD_COMMENT;
        } else if (items.get(position - NUMBER_OF_VIEW_ABOVE).getReplies().size() == 1) {
            return VIEW_TYPE_ONE_REPLY;
        } else if (items.get(position - NUMBER_OF_VIEW_ABOVE).getReplies().size() == 2) {
            return VIEW_TYPE_TWO_REPLIES;
        } else {
            return VIEW_TYPE_ALL_REPLIES;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        for (AdapterDelegate<List<ThreadCommentModel>> delegate : delegates) {
            if (delegate.getViewType() == viewType) {
                return delegate.onCreateViewHolder(parent);
            }
        }
        throw new IllegalArgumentException("No delegate found");
    }

    @Override
    public void onBindViewHolder(@NonNull List<ThreadCommentModel> items, int position, @NonNull RecyclerView.ViewHolder viewHolder) {
        int viewType = viewHolder.getItemViewType();

        for (AdapterDelegate<List<ThreadCommentModel>> delegate : delegates) {
            if (delegate.getViewType() == viewType) {
                delegate.onBindViewHolder(items, position - NUMBER_OF_VIEW_ABOVE, viewHolder);
                break;
            }
        }
    }

    @Override
    public int getItemCount(@NonNull List<ThreadCommentModel> items) {
        return items.size() + NUMBER_OF_VIEW_ABOVE;
    }
}
