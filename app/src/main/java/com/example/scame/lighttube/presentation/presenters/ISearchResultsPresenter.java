package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.SearchItemModel;

import java.util.List;

public interface ISearchResultsPresenter<V> extends Presenter<V> {

    interface SearchResultsView {

        void initializeAdapter(List<SearchItemModel> searchItems);

        void updateAdapter(List<SearchItemModel> searchItems);
    }

    void fetchVideos(int page, String query);
}
