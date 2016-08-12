package com.example.scame.lighttubex.presentation.presenters;


import com.example.scame.lighttubex.data.entities.search.SearchEntity;

public interface ISearchResultsPresenter<V> extends Presenter<V> {

    interface SearchResultsView {

        void initializeAdapter(SearchEntity searchEntity);

        void updateAdapter(SearchEntity searchEntity);
    }

    void fetchVideos(int page, SearchEntity searchEntity, String query);
}
