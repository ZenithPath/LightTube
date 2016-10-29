package com.example.scame.lighttube.presentation.adapters.player.replies;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.adapters.player.PopupHandler;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.List;

public class HeaderCommentDelegate implements AdapterDelegate<List<?>> {

    private PopupHandler popupHandler;

    public HeaderCommentDelegate(PopupHandler popupHandler) {
        this.popupHandler = popupHandler;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.primary_comment_item, parent, false);
        return new HeaderCommentHolder(itemView, popupHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull List<?> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        int relativePosition = position + RepliesDelegatesManager.NUMBER_OF_VIEW_ABOVE;

        ThreadCommentModel threadCommentModel = (ThreadCommentModel) items.get(relativePosition);
        ((HeaderCommentHolder) holder).bindHeaderCommentHolder(threadCommentModel);
    }

    @Override
    public int getViewType() {
        return RepliesDelegatesManager.VIEW_TYPE_HEADER_COMMENT;
    }
}
