package com.example.scame.lighttubex.presentation.presenters;


import com.example.scame.lighttubex.presentation.model.VideoItemModel;

import java.util.List;

public interface IVideoListPresenter<V> extends Presenter<V>{

    interface VideoListView {

        void initializeAdapter(List<VideoItemModel> items);

        void updateAdapter(List<VideoItemModel> items);
    }

    void fetchVideos(int page, List<VideoItemModel> savedItems);
}
