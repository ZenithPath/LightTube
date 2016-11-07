package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.RecentVideosRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.ChannelModel;

import java.util.List;

import rx.Observable;

public class GetSubscriptionsUseCase extends UseCase<List<ChannelModel>> {

    private RecentVideosRepository recentVideosRepository;

    public GetSubscriptionsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                   RecentVideosRepository recentVideosRepository) {
        super(subscribeOn, observeOn);
        this.recentVideosRepository = recentVideosRepository;
    }

    @Override
    protected Observable<List<ChannelModel>> getUseCaseObservable() {
        return recentVideosRepository.getChannels();
    }
}
