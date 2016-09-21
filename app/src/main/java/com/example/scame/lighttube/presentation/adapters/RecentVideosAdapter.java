package com.example.scame.lighttube.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.model.VideoModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecentVideosAdapter extends BaseAdapter {

    private List<VideoModel> items;
    private Context context;

    private static OnItemClickListener listener;

    public RecentVideosAdapter(List<VideoModel> items, Context context, RecyclerView recyclerView) {
        super(recyclerView, context, items);
        this.items = items;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setupOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recent_video_item_iv) ImageView thumbnailsIv;
        @BindView(R.id.recent_video_title) TextView titleTv;
        @BindView(R.id.recent_video_date) TextView dateTv;

        public ViewHolder(View itemView) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View videoItemView = inflater.inflate(R.layout.recent_video_item, parent, false);

        return new ViewHolder(videoItemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderArg, int position) {
        ViewHolder viewHolder = (ViewHolder) holderArg;

        VideoModel videoModel = items.get(position);

        ImageView imageView = viewHolder.thumbnailsIv;
        TextView titleTv = viewHolder.titleTv;
        TextView dateTv = viewHolder.dateTv;

        titleTv.setText(videoModel.getTitle());
        dateTv.setText(videoModel.getPublishedAt());

        Picasso.with(context).load(videoModel.getImageUrl())
                .placeholder(R.drawable.colors_0011_pearl_grey)
                .resize(650, 400)
                .centerCrop()
                .into(imageView);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public Context getContext() {
        return context;
    }
}