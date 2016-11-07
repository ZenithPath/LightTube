package com.example.scame.lighttube.data.interceptors;

import com.example.scame.lighttube.data.repository.AccountRepository;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    private AccountRepository accountRepository;

    private String newToken;

    public TokenAuthenticator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        updateToken();

        return response.request().newBuilder()
                .header("Authorization", "Bearer " + newToken)
                .build();
    }

    private void updateToken() {
        accountRepository.getTokenFromCache()
                .filter(tokenEntity -> !tokenEntity.getRefreshToken().equals(""))
                .doOnNext(accountRepository::refreshToken)
                .flatMap(refreshedEntity -> accountRepository.getTokenFromCache())
                .subscribe(finalEntity -> newToken = finalEntity.getAccessToken());
    }
}
