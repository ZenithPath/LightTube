package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.ModelMarker;

import java.util.List;

public interface ChannelVideosPresenter<T> extends Presenter<T> {

    interface ChannelsView {

        void initializeAdapter(List<? extends ModelMarker> searchItemModels);

        void updateAdapter(List<? extends ModelMarker> searchItemModels);
    }

    void fetchChannelVideos(String channelId, int page);
}
