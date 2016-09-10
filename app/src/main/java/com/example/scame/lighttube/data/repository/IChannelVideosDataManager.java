package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.data.entities.search.SearchEntity;

import rx.Observable;

public interface IChannelVideosDataManager {

    Observable<SearchEntity> getChannelVideos(String channelId, int page);
}
