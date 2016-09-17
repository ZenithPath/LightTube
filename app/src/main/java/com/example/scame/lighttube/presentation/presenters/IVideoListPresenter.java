package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.ModelMarker;

import java.util.List;

public interface IVideoListPresenter<V> extends Presenter<V>{

    interface VideoListView {

        void initializeAdapter(List<? extends ModelMarker> items);

        void updateAdapter(List<? extends ModelMarker> items);
    }

    void fetchVideos(int page);
}
