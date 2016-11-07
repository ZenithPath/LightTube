package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import rx.Observable;

public interface ChannelVideosRepository {

    Observable<VideoModelsWrapper> getChannelVideos(String channelId, int page);
}
