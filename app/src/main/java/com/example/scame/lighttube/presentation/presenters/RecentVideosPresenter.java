package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.ChannelModel;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

public interface RecentVideosPresenter<T> extends Presenter<T> {

    interface RecentVideosView {

        void visualizeChannelsList(List<ChannelModel> channelModels);

        void populateAdapter(List<VideoModel> items);

        void updateAdapter(List<VideoModel> items);
    }

    void getChannelsList();

    void getRecentVideosList();
}
