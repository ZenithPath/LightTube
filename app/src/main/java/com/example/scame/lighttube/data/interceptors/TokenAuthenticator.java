package com.example.scame.lighttube.data.interceptors;

import com.example.scame.lighttube.data.repository.IAccountDataManager;
import com.example.scame.lighttube.presentation.LightTubeApp;

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
        IAccountDataManager dataManager = LightTubeApp.getAppComponent().getSignInDataManager();

        dataManager.getTokenEntity()
                .filter(tokenEntity -> !tokenEntity.getRefreshToken().equals(""))
                .doOnNext(dataManager::refreshToken)
                .flatMap(tokenEntity2 -> dataManager.getTokenEntity())
                .subscribe(tokenEntity3 -> newToken = tokenEntity3.getAccessToken());
    }
}
