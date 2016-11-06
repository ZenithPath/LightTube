package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegatesManager;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;
import com.example.scame.lighttube.presentation.fragments.PlayerFooterFragment;
import com.example.scame.lighttube.presentation.model.HeaderModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.ArrayList;
import java.util.List;

public class CommentsDelegatesManager implements AdapterDelegatesManager<List<?>> {

    public static final int NUMBER_OF_VIEW_ABOVE_COUNTS = 1;
    public static final int NUMBER_OF_VIEW_ABOVE = 2;

    static final int VIEW_TYPE_HEADER = 0;
    static final int VIEW_TYPE_THREAD_COMMENT = 1;
    static final int VIEW_TYPE_ONE_REPLY = 2;
    static final int VIEW_TYPE_TWO_REPLIES = 3;
    static final int VIEW_TYPE_ALL_REPLIES = 4;
    static final int VIEW_TYPE_COMMENT_INPUT = 5;
    static final int VIEW_TYPE_EDIT_COMMENT = 6;
    static final int VIEW_TYPE_COMMENTS_COUNT = 7;
    static final int VIEW_TYPE_PROGRESS = 8;

    private static final int HEADER_LAYOUT_POSITION = 0;
    private static final int COMMENTS_COUNT_POSITION = 1;
    private static final int COMMENT_INPUT_POSITION = 2;

    private List<AdapterDelegate<List<?>>> delegates;

    public CommentsDelegatesManager(CommentActionListener actionListener, Context context,
                                    String userIdentifier, HeaderModel headerModel,
                                    PlayerFooterFragment.PlayerFooterListener footerListener,
                                    PlayerFooterFragment.PostedCommentListener postedCommentListener,
                                    View.OnClickListener orderListener) {
        delegates = new ArrayList<>();

        delegates.add(new CommentInputDelegate(postedCommentListener));
        delegates.add(new ThreadCommentDelegate(actionListener, userIdentifier));
        delegates.add(new SingleReplyDelegate(actionListener, userIdentifier));
        delegates.add(new CoupleRepliesDelegate(actionListener, userIdentifier));
        delegates.add(new MultipleRepliesDelegate(footerListener, actionListener, userIdentifier));
        delegates.add(new HeaderDelegate(context, headerModel));
        delegates.add(new EditCommentDelegate(actionListener));
        delegates.add(new CommentsCountDelegate(orderListener));
        delegates.add(new ProgressDelegate(VIEW_TYPE_PROGRESS));
    }

    @Override
    public AdapterDelegatesManager<List<?>> addDelegate(@NonNull AdapterDelegate<List<?>> delegate) {
        delegates.add(delegate);
        return this;
    }

    @Override
    public int getItemViewType(@NonNull List<?> items, int position) {
        ThreadCommentModel commentModel = getThreadModelByPosition(items, position);
        Integer commentsCount = getCommentsCountByPosition(items, position);

        if (position == HEADER_LAYOUT_POSITION) {
            return VIEW_TYPE_HEADER;
        } else if (position == COMMENT_INPUT_POSITION) {
            return VIEW_TYPE_COMMENT_INPUT;
        } else if (commentsCount != null) {
            return VIEW_TYPE_COMMENTS_COUNT;
        } else if (items.get(position - NUMBER_OF_VIEW_ABOVE) instanceof UpdateCommentObj) {
            return VIEW_TYPE_EDIT_COMMENT;
        } else if (items.get(position - NUMBER_OF_VIEW_ABOVE) == null) {
            return VIEW_TYPE_PROGRESS;
        } else if (commentModel != null && commentModel.getReplies().size() == 0) {
            return VIEW_TYPE_THREAD_COMMENT;
        } else if (commentModel != null && commentModel.getReplies().size() == 1) {
            return VIEW_TYPE_ONE_REPLY;
        } else if (commentModel != null && commentModel.getReplies().size() == 2) {
            return VIEW_TYPE_TWO_REPLIES;
        } else {
            return VIEW_TYPE_ALL_REPLIES;
        }
    }

    private ThreadCommentModel getThreadModelByPosition(List<?> items, int position) {
        int relativePosition = position - NUMBER_OF_VIEW_ABOVE;
        if (relativePosition >= 0 && items.get(relativePosition) instanceof ThreadCommentModel) {
            return (ThreadCommentModel) items.get(relativePosition);
        }
        return null;
    }

    private Integer getCommentsCountByPosition(List<?> items, int position) {
        int relativePosition = position - NUMBER_OF_VIEW_ABOVE_COUNTS;
        if (relativePosition >= 0 && relativePosition < items.size() && items.get(relativePosition) instanceof Integer) {
            return (Integer) items.get(relativePosition);
        }
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        for (AdapterDelegate<List<?>> delegate : delegates) {
            if (delegate.getViewType() == viewType) {
                return delegate.onCreateViewHolder(parent);
            }
        }
        throw new IllegalArgumentException("No delegate found");
    }

    @Override
    public void onBindViewHolder(@NonNull List<?> items, int position, @NonNull RecyclerView.ViewHolder viewHolder) {
        int viewType = viewHolder.getItemViewType();

        for (AdapterDelegate<List<?>> delegate : delegates) {
            if (delegate.getViewType() == viewType) {
                delegate.onBindViewHolder(items, position - NUMBER_OF_VIEW_ABOVE, viewHolder);
                break;
            }
        }
    }

    @Override
    public int getItemCount(@NonNull List<?> items) {
        return items.size() == 0 ? 0 : items.size() + NUMBER_OF_VIEW_ABOVE;
    }
}
