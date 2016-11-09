package com.example.scame.lighttube.presentation.presenters;


import java.util.List;

public interface SearchResultsPresenter<V> extends Presenter<V> {

    interface SearchResultsView {

        void initializeAdapter(List<?> searchItems);

        void updateAdapter(List<?> searchItems);
    }

    void fetchVideos(int page, String query);
}
