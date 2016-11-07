package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.UserChannelRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import rx.Observable;

public class GetUserIdentifierUseCase extends UseCase<String> {

    private UserChannelRepository dataManager;

    public GetUserIdentifierUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, UserChannelRepository dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<String> getUseCaseObservable() {
        return dataManager.getUserChannelUrl();
    }
}
