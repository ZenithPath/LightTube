package com.example.scame.lighttube.domain.usecases;

import com.example.scame.lighttube.data.entities.TokenEntity;
import com.example.scame.lighttube.data.repository.IAccountDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import javax.inject.Inject;

import rx.Observable;


public class SignInUseCase extends UseCase<TokenEntity> {

    private IAccountDataManager accountDataManager;

    private String serverAuthCode;

    @Inject
    public SignInUseCase(IAccountDataManager accountDataManager, SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(subscribeOn, observeOn);

        this.accountDataManager = accountDataManager;
    }

    @Override
    protected Observable<TokenEntity> getUseCaseObservable() {
        return accountDataManager.getToken(serverAuthCode);
    }

    public void setServerAuthCode(String serverAuthCode) {
        this.serverAuthCode = serverAuthCode;
    }
}
