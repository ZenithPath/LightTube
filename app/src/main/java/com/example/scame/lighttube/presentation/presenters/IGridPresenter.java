package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.SearchItemModel;

import java.util.List;

public interface IGridPresenter<V> extends Presenter<V> {

    interface GridView {

        void populateAdapter(List<SearchItemModel> items);

        void updateAdapter(List<SearchItemModel> items);
    }

    void fetchVideos(String category, String duration, int page);
}
