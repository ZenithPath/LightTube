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

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private Context context;

    private static OnItemClickListener clickListener;

    private List<SearchItemModel> items;

    public GridAdapter(Context context, List<SearchItemModel> items) {
        this.context = context;
        this.items = items;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.grid_item_iv) ImageView thumbnails;
        @BindView(R.id.grid_item_tv) TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v ->
                clickListener.onItemClick(itemView, getLayoutPosition())
            );

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View gridItemView = inflater.inflate(R.layout.grid_item, parent, false);

        return new ViewHolder(gridItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchItemModel searchItem = items.get(position);

        ImageView imageView = holder.thumbnails;
        TextView textView = holder.title;

        Picasso.with(context).load(searchItem.getImageUrl()).resize(300, 225).centerCrop().into(imageView);
        textView.setText(searchItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
