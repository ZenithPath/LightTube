package com.example.scame.lighttubex.presentation.presenters;


import com.example.scame.lighttubex.presentation.model.SearchItemModel;

import java.util.List;

public interface ISearchResultsPresenter<V> extends Presenter<V> {

    interface SearchResultsView {

        void initializeAdapter(List<SearchItemModel> searchItems);

        void updateAdapter(List<SearchItemModel> searchItems);
    }

    void fetchVideos(int page, List<SearchItemModel> savedItems, String query);
}
