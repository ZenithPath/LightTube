package com.example.scame.lighttube.domain.usecases;

import com.example.scame.lighttube.data.entities.TokenEntity;
import com.example.scame.lighttube.data.repository.AccountRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import javax.inject.Inject;

import rx.Observable;


public class SignInUseCase extends UseCase<TokenEntity> {

    private AccountRepository accountRepository;

    private String serverAuthCode;

    @Inject
    public SignInUseCase(AccountRepository accountRepository, SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(subscribeOn, observeOn);
        this.accountRepository = accountRepository;
    }

    @Override
    protected Observable<TokenEntity> getUseCaseObservable() {
        return accountRepository.getToken(serverAuthCode);
    }

    public void setServerAuthCode(String serverAuthCode) {
        this.serverAuthCode = serverAuthCode;
    }

    public String getServerAuthCode() {
        return serverAuthCode;
    }
}
