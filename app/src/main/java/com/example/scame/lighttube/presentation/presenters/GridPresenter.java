package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.ModelMarker;

import java.util.List;

public interface GridPresenter<V> extends Presenter<V> {

    interface GridView {

        void initializeAdapter(List<? extends ModelMarker> items);

        void updateAdapter(List<? extends ModelMarker> items);
    }

    void fetchVideos(String category, String duration, int page);
}
