package com.example.scame.lighttubex.domain.usecases;

import com.example.scame.lighttubex.data.entities.TokenEntity;
import com.example.scame.lighttubex.data.repository.ISignInDataManager;
import com.example.scame.lighttubex.domain.schedulers.ObserveOn;
import com.example.scame.lighttubex.domain.schedulers.SubscribeOn;
import com.example.scame.lighttubex.presentation.di.PerActivity;

import javax.inject.Inject;

import rx.Observable;

@PerActivity
public class SignInUseCase extends UseCase<TokenEntity> {

    private ISignInDataManager signInDataManager;

    private String serverAuthCode;

    @Inject
    public SignInUseCase(ISignInDataManager signInDataManager,
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
