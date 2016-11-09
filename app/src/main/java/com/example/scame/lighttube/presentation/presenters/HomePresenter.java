package com.example.scame.lighttube.presentation.presenters;


import java.util.List;

public interface HomePresenter<V> extends Presenter<V>{

    interface VideoListView {

        void initializeAdapter(List<?> items);

        void updateAdapter(List<?> items);
    }

    void fetchVideos(int page);
}
