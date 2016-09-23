package com.example.scame.lighttube.domain.usecases;

import com.example.scame.lighttube.data.repository.IAccountDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import rx.Observable;

public class SignOutUseCase extends UseCase<Void> {

    private IAccountDataManager accountDataManager;

    public SignOutUseCase(IAccountDataManager accountDataManager, SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(subscribeOn, observeOn);

        this.accountDataManager = accountDataManager;
    }

    @Override
    protected Observable<Void> getUseCaseObservable() {
        return accountDataManager.signOut();
    }
}
