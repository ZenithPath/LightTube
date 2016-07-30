package com.example.scame.lighttubex.data.interceptors;

import com.example.scame.lighttubex.data.repository.ISignInDataManager;
import com.example.scame.lighttubex.presentation.LightTubeApp;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    private String newToken;

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        updateToken();

        return response.request().newBuilder()
                .header("Authorization", "Bearer " + newToken)
                .build();
    }

    private void updateToken() {
        ISignInDataManager dataManager = LightTubeApp.getAppComponent().getSignInDataManager();

        dataManager.getTokenEntity()
                .filter(tokenEntity -> !tokenEntity.getRefreshToken().equals(""))
                .doOnNext(dataManager::refreshToken)
                .flatMap(tokenEntity2 -> dataManager.getTokenEntity())
                .subscribe(tokenEntity3 -> newToken = tokenEntity3.getAccessToken());
    }
}
