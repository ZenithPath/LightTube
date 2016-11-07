package com.example.scame.lighttube.domain.usecases;

import com.example.scame.lighttube.data.repository.AccountRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import rx.Observable;

public class SignOutUseCase extends UseCase<Void> {

    private AccountRepository accountRepository;

    public SignOutUseCase(AccountRepository accountRepository, SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(subscribeOn, observeOn);
        this.accountRepository = accountRepository;
    }

    @Override
    protected Observable<Void> getUseCaseObservable() {
        return accountRepository.signOut();
    }
}
