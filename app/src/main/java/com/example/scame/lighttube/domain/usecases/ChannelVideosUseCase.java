package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.mappers.SearchListMapper;
import com.example.scame.lighttube.data.repository.IChannelVideosDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public class ChannelVideosUseCase extends UseCase<List<VideoModel>> {

    private IChannelVideosDataManager dataManager;

    private String channelId;

    private int page;

    public ChannelVideosUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                IChannelVideosDataManager dataManager) {

        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<List<VideoModel>> getUseCaseObservable() {
        SearchListMapper mapper = new SearchListMapper();

        return dataManager.getChannelVideos(channelId, page)
                .map(mapper::convert);
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
