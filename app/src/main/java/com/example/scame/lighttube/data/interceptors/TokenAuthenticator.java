package com.example.scame.lighttube.data.interceptors;

import com.example.scame.lighttube.data.repository.IAccountDataManager;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    private IAccountDataManager accountDataManager;

    private String newToken;

    public TokenAuthenticator(IAccountDataManager accountDataManager) {
        this.accountDataManager = accountDataManager;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        updateToken();

        return response.request().newBuilder()
                .header("Authorization", "Bearer " + newToken)
                .build();
    }

    private void updateToken() {
        accountDataManager.getTokenEntity()
                .filter(tokenEntity -> !tokenEntity.getRefreshToken().equals(""))
                .doOnNext(accountDataManager::refreshToken)
                .flatMap(refreshedEntity -> accountDataManager.getTokenEntity())
                .subscribe(finalEntity -> newToken = finalEntity.getAccessToken());
    }
}
