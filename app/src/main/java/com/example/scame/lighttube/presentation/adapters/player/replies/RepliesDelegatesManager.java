package com.example.scame.lighttube.presentation.adapters.player.replies;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegatesManager;
import com.example.scame.lighttube.presentation.adapters.player.PopupHandler;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;
import com.example.scame.lighttube.presentation.fragments.RepliesFragment;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.ArrayList;
import java.util.List;

public class RepliesDelegatesManager implements AdapterDelegatesManager<List<?>> {

    public static final int VIEW_TYPE_REPLY_COMMENT = 0;
    public static final int VIEW_TYPE_REPLY_INPUT = 1;
    public static final int VIEW_TYPE_EDIT_REPLY = 2;
    public static final int VIEW_TYPE_HEADER_COMMENT = 3;

    public static final int NUMBER_OF_VIEW_ABOVE = 1;

    public static final int REPLY_INPUT_POS = 1;

    public static final int HEADER_COMMENT_POS = 0;

    private List<AdapterDelegate<List<?>>> delegates;

    public RepliesDelegatesManager(RepliesFragment.RepliesListener replyInputListener,
                                   CommentActionListener commentActionListener,
                                   String userIdentifier) {
        delegates = new ArrayList<>();
        delegates.add(new RepliesViewDelegate(new PopupHandler(commentActionListener, userIdentifier)));
        delegates.add(new HeaderCommentDelegate(new PopupHandler(commentActionListener, userIdentifier)));
        delegates.add(new RepliesInputDelegate(replyInputListener));
        delegates.add(new EditReplyDelegate(commentActionListener));
    }

    public RepliesDelegatesManager() {
        delegates = new ArrayList<>();
    }

    @Override
    public AdapterDelegatesManager<List<?>> addDelegate(@NonNull AdapterDelegate<List<?>> delegate) {
        delegates.add(delegate);
        return this;
    }

    @Override
    public int getItemViewType(@NonNull List<?> items, int position) {

        if (position == HEADER_COMMENT_POS && items.get(HEADER_COMMENT_POS) instanceof ThreadCommentModel) {
            return VIEW_TYPE_HEADER_COMMENT;
        } else if (position == HEADER_COMMENT_POS && items.get(HEADER_COMMENT_POS) instanceof UpdateReplyObj) {
            return VIEW_TYPE_EDIT_REPLY;
        } else if (position == REPLY_INPUT_POS) {
            return VIEW_TYPE_REPLY_INPUT;
        } else if (items.get(position - NUMBER_OF_VIEW_ABOVE) instanceof UpdateReplyObj) {
            return VIEW_TYPE_EDIT_REPLY;
        } else {
            return VIEW_TYPE_REPLY_COMMENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        for (AdapterDelegate<List<?>> delegate : delegates) {
            if (delegate.getViewType() == viewType) {
                return delegate.onCreateViewHolder(parent);
            }
        }
        throw new IllegalArgumentException("No delegate with type " + viewType + " found");
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

    // FIXME: this hack was made to give an adapter a time to bound inputView and only then make it active
    public void setAuthorName(boolean asReply, String authorName) {
        for (AdapterDelegate<List<?>> delegate : delegates) {
            if (delegate instanceof RepliesInputDelegate) {
                ((RepliesInputDelegate) delegate).setModeFields(asReply, authorName);
                break;
            }
        }
    }
}
