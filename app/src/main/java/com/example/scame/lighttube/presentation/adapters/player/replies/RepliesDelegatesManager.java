package com.example.scame.lighttube.presentation.adapters.player.replies;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegatesManager;
import com.example.scame.lighttube.presentation.adapters.player.PopupHandler;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;
import com.example.scame.lighttube.presentation.fragments.RepliesFragment;
import com.example.scame.lighttube.presentation.model.ReplyListModel;

import java.util.ArrayList;
import java.util.List;

public class RepliesDelegatesManager implements AdapterDelegatesManager<ReplyListModel> {

    public static final int VIEW_TYPE_REPLY_COMMENT = 0;
    public static final int VIEW_TYPE_REPLY_INPUT = 1;
    public static final int VIEW_TYPE_EDIT_REPLY = 2;

    public static final int NUMBER_OF_VIEW_ABOVE = 1;

    public static final int REPLY_INPUT_POS = 0;

    private List<AdapterDelegate<ReplyListModel>> delegates;

    public RepliesDelegatesManager(RepliesFragment.RepliesListener replyInputListener,
                                   CommentActionListener commentActionListener,
                                   String userIdentifier) {
        delegates = new ArrayList<>();
        delegates.add(new RepliesViewDelegate(new PopupHandler(commentActionListener, userIdentifier)));
        delegates.add(new RepliesInputDelegate(replyInputListener));
        delegates.add(new EditReplyDelegate(commentActionListener));
    }

    public RepliesDelegatesManager() {
        delegates = new ArrayList<>();
    }

    @Override
    public AdapterDelegatesManager<ReplyListModel> addDelegate(@NonNull AdapterDelegate<ReplyListModel> delegate) {
        delegates.add(delegate);
        return this;
    }

    @Override
    public int getItemViewType(@NonNull ReplyListModel items, int position) {
        if (position == REPLY_INPUT_POS) {
            return VIEW_TYPE_REPLY_INPUT;
        } else if (items.getReplyModel(position - NUMBER_OF_VIEW_ABOVE) instanceof UpdateReplyModelHolder) {
            return VIEW_TYPE_EDIT_REPLY;
        } else {
            return VIEW_TYPE_REPLY_COMMENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        for (AdapterDelegate<ReplyListModel> delegate : delegates) {
            if (delegate.getViewType() == viewType) {
                return delegate.onCreateViewHolder(parent);
            }
        }
        throw new IllegalArgumentException("No delegate found");
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyListModel items, int position, @NonNull RecyclerView.ViewHolder viewHolder) {
        int viewType = viewHolder.getItemViewType();

        for (AdapterDelegate<ReplyListModel> delegate : delegates) {
            if (delegate.getViewType() == viewType) {
                delegate.onBindViewHolder(items, position - NUMBER_OF_VIEW_ABOVE, viewHolder);
                break;
            }
        }
    }

    @Override
    public int getItemCount(@NonNull ReplyListModel items) {
        return items.getReplyModels().size() + NUMBER_OF_VIEW_ABOVE;
    }

    // FIXME: this hack was made to give an adapter a time to bound inputView and only then make it active
    public void setModeFields(boolean asReply, int position) {
        for (AdapterDelegate<ReplyListModel> delegate : delegates) {
            if (delegate instanceof RepliesInputDelegate) {
                ((RepliesInputDelegate) delegate).setModeFields(asReply, position);
                break;
            }
        }
    }
}
