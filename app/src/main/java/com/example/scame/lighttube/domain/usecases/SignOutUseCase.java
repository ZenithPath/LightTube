package com.example.scame.lighttube.domain.usecases;

import com.example.scame.lighttube.data.repository.IAccountDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import rx.Observable;

public class SignOutUseCase extends UseCase<Void> {

    private IAccountDataManager dataManager;

    public SignOutUseCase(IAccountDataManager dataManager, SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(subscribeOn, observeOn);

        this.dataManager = dataManager;
    }

    @Override
    protected Observable<Void> getUseCaseObservable() {
        return dataManager.signOut();
    }
}
