package com.example.scame.lighttube.presentation.presenters;


import java.util.List;

public interface GridPresenter<V> extends Presenter<V> {

    interface GridView {

        void initializeAdapter(List<?> items);

        void updateAdapter(List<?> items);
    }

    void fetchVideos(String category, String duration, int page);
}
