package com.example.scame.lighttube.domain.usecases;

import com.example.scame.lighttube.data.entities.TokenEntity;
import com.example.scame.lighttube.data.repository.IAccountDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.di.PerActivity;

import javax.inject.Inject;

import rx.Observable;

@PerActivity
public class SignInUseCase extends UseCase<TokenEntity> {

    private IAccountDataManager signInDataManager;

    private String serverAuthCode;

    @Inject
    public SignInUseCase(IAccountDataManager signInDataManager,
                         SubscribeOn subscribeOn, ObserveOn observeOn) {

        super(subscribeOn, observeOn);
        this.signInDataManager = signInDataManager;
    }

    @Override
    protected Observable<TokenEntity> getUseCaseObservable() {

        if (signInDataManager.ifTokenExists()) {
            return signInDataManager.getTokenEntity();
        }

        return signInDataManager.fetchWithServerAuthCode(serverAuthCode)
                .doOnNext(tokenEntity -> signInDataManager.saveTokens(tokenEntity, true));
    }

    public void setServerAuthCode(String serverAuthCode) {
        this.serverAuthCode = serverAuthCode;
    }
}
