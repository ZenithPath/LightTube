package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.ModelMarker;

import java.util.List;

public interface SearchResultsPresenter<V> extends Presenter<V> {

    interface SearchResultsView {

        void initializeAdapter(List<? extends ModelMarker> searchItems);

        void updateAdapter(List<? extends ModelMarker> searchItems);
    }

    void fetchVideos(int page, String query);
}
