package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.ChannelVideosRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import rx.Observable;

public class GetChannelVideosUseCase extends UseCase<VideoModelsWrapper> {

    private ChannelVideosRepository videosDataManager;

    private String channelId;

    private int page;

    public GetChannelVideosUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                   ChannelVideosRepository videosDataManager) {
        super(subscribeOn, observeOn);
        this.videosDataManager = videosDataManager;
    }

    @Override
    protected Observable<VideoModelsWrapper> getUseCaseObservable() {
        return videosDataManager.getChannelVideos(channelId, page);
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getChannelId() {
        return channelId;
    }

    public int getPage() {
        return page;
    }
}
