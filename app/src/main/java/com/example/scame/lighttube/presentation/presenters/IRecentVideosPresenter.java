package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.ChannelModel;
import com.example.scame.lighttube.presentation.model.SearchItemModel;

import java.util.List;

public interface IRecentVideosPresenter<T> extends Presenter<T> {

    interface RecentVideosView {

        void visualizeChannelsList(List<ChannelModel> channelModels);

        void populateAdapter(List<SearchItemModel> items);

        void updateAdapter(List<SearchItemModel> items);
    }

    void initialize();
}
