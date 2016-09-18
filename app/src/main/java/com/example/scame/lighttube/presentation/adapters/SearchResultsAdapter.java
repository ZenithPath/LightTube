package com.example.scame.lighttube.presentation.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.model.SearchItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsAdapter extends BaseAdapter {

    private static BaseAdapter.OnItemClickListener listener;

    public SearchResultsAdapter(List<?> items, Context context, RecyclerView recyclerView) {
        super(recyclerView, context, items);
    }

    public static class SearchViewHolder extends  RecyclerView.ViewHolder {

        @BindView(R.id.search_item_iv) ImageView searchIv;
        @BindView(R.id.search_item_title) TextView titleTv;

        public SearchViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onItemClick(view, getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof NoConnectionMarker) {
            return VIEW_TYPE_NO_CONNECTION;
        } else if (items.get(position) instanceof SearchItemModel) {
            return VIEW_TYPE_VIDEO;
        } else if (items.get(position) == null) {
            return VIEW_TYPE_PROGRESS;
        }

        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_VIDEO) {
            View videoView = inflater.inflate(R.layout.search_result_item, parent, false);
            viewHolder = new SearchViewHolder(videoView);
        } else if (viewType == VIEW_TYPE_PROGRESS) {
            View progressView = inflater.inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressViewHolder(progressView);
        } else if (viewType == VIEW_TYPE_NO_CONNECTION) {
            View noConnectionView = inflater.inflate(R.layout.no_internet_list_item, parent, false);
            viewHolder = new NoConnectionViewHolder(noConnectionView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SearchViewHolder) {
            SearchItemModel searchItem = (SearchItemModel) items.get(position);
            SearchViewHolder viewHolder = (SearchViewHolder) holder;

            ImageView imageView = viewHolder.searchIv;
            TextView textView = viewHolder.titleTv;

            Picasso.with(context).load(searchItem.getImageUrl()).into(imageView);
            textView.setText(searchItem.getTitle());

        } else if (holder instanceof ProgressViewHolder) {
            ProgressViewHolder progressHolder = (ProgressViewHolder) holder;
            progressHolder.progressBar.setIndeterminate(true);
        } else if (holder instanceof NoConnectionViewHolder) {
            NoConnectionViewHolder noConnectionViewHolder = (NoConnectionViewHolder) holder;
            noConnectionViewHolder.setClickListener(onRetryClickListener);
        }
    }

    public void setupOnItemClickListener(OnItemClickListener listener) {
        SearchResultsAdapter.listener = listener;
    }
}
