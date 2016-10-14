package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.IUserChannelDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import rx.Observable;

public class RetrieveUserIdentifierUseCase extends UseCase<String> {

    private IUserChannelDataManager dataManager;

    public RetrieveUserIdentifierUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, IUserChannelDataManager dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<String> getUseCaseObservable() {
        return dataManager.getUserChannelUrl();
    }
}
