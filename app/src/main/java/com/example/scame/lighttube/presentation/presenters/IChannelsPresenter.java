package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.SearchItemModel;

import java.util.List;

public interface IChannelsPresenter<T> extends Presenter<T> {

    interface ChannelsView {

        void populateAdapter(List<SearchItemModel> videoItemModels);
    }

    void fetchChannelVideos(String channelId);
}
