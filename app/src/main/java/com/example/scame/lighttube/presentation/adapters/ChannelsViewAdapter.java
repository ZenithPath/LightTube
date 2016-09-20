package com.example.scame.lighttube.presentation.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.model.ChannelModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChannelsViewAdapter extends RecyclerView.Adapter<ChannelsViewAdapter.ViewHolder>{

    private List<ChannelModel> items;
    private Context context;

    private static OnItemClickListener listener;

    public ChannelsViewAdapter(List<ChannelModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onChannelClick(View itemView, int position);
    }

    public void setupOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.channel_iv)
        ImageView thumbnailsIv;

        public ViewHolder(View itemView) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View videoItemView = inflater.inflate(R.layout.channel_item, parent, false);

        return new ViewHolder(videoItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChannelModel item = items.get(position);

        ImageView imageView = holder.thumbnailsIv;

        Picasso.with(context).load(item.getImageUrl())
                .placeholder(R.drawable.colors_0011_pearl_grey)
                .noFade()
                .resize(70, 70)
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
