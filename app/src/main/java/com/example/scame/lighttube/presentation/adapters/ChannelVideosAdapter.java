package com.example.scame.lighttube.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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

public class ChannelVideosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_PROGRESS = 0;
    private static final int VIEW_TYPE_VIDEO = 1;

    private List<SearchItemModel> items;
    private Context context;

    private static OnItemClickListener listener;

    private OnLoadMoreListener onLoadMoreListener;

    private int visibleThreshold = 3;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

    private int currentPage;

    public ChannelVideosAdapter(List<SearchItemModel> items, Context context, RecyclerView recyclerView) {
        this.items = items;
        this.context = context;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore(++currentPage);
                        }

                        loading = true;
                    }
                }
            });
        }
    }

    public void setLoaded() {
        loading = false;
    }

    public void setPage(int page) {
        this.currentPage = page;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnItemClickListener {
        void onChannelClick(View itemView, int position);
    }

    public void setupOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ChannelVideosHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.channels_item_iv) ImageView thumbnailsIv;
        @BindView(R.id.channels_item_title) TextView title;

        public ChannelVideosHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onChannelClick(view, getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_PROGRESS : VIEW_TYPE_VIDEO;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_VIDEO) {
            View videoView = inflater.inflate(R.layout.channels_fragment_item, parent, false);
            viewHolder = new ChannelVideosHolder(videoView);
        } else if (viewType == VIEW_TYPE_PROGRESS) {
            View progressView = inflater.inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressViewHolder(progressView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ChannelVideosHolder) {
            ChannelVideosHolder channelVideosHolder = (ChannelVideosHolder) holder;
            SearchItemModel item = items.get(position);

            ImageView imageView = channelVideosHolder.thumbnailsIv;
            TextView textView = channelVideosHolder.title;

            Picasso.with(context).load(item.getImageUrl()).resize(650, 400).centerCrop().into(imageView);
            textView.setText(item.getTitle());
        } else if (holder instanceof ProgressViewHolder) {
            ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Context getContext() {
        return context;
    }
}
