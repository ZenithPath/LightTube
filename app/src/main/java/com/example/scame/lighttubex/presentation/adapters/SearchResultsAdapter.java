package com.example.scame.lighttubex.presentation.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.presentation.model.SearchItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private Context context;
    private List<SearchItemModel> searchItems;

    private static OnItemClickListener listener;

    public static class ViewHolder extends  RecyclerView.ViewHolder {

        @BindView(R.id.search_item_iv) ImageView searchIv;
        @BindView(R.id.search_item_title) TextView titleTv;

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

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public SearchResultsAdapter(List<SearchItemModel> items, Context context) {
        this.searchItems = items;
        this.context = context;
    }

    public void setupOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View searchItemView = inflater.inflate(R.layout.search_result_item, parent, false);

        return new ViewHolder(searchItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchItemModel searchItem = searchItems.get(position);

        ImageView imageView = holder.searchIv;
        TextView textView = holder.titleTv;

        Picasso.with(context).load(searchItem.getImageUrl()).resize(500, 275).centerCrop().into(imageView);
        textView.setText(searchItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return searchItems.size();
    }
}
