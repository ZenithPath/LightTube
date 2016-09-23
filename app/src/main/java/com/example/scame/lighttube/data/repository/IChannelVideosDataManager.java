package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public interface IChannelVideosDataManager {

    Observable<List<VideoModel>> getChannelVideos(String channelId, int page);
}
