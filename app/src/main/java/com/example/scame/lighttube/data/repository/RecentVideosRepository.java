package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.ChannelModel;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import java.util.List;

import rx.Observable;

public interface RecentVideosRepository {

    Observable<VideoModelsWrapper> getRecentVideos();

    Observable<List<ChannelModel>> getChannels();
}
