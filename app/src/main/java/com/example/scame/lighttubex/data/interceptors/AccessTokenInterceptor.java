package com.example.scame.lighttubex.data.interceptors;

import com.example.scame.lighttubex.data.repository.ISignInDataManager;
import com.example.scame.lighttubex.presentation.LightTubeApp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AccessTokenInterceptor implements Interceptor {

    private String accessToken;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request authorisedRequest;

        ISignInDataManager dataManager = LightTubeApp.getAppComponent().getSignInDataManager();
        dataManager.getTokenEntity()
                .subscribe(tokenEntity -> accessToken = tokenEntity.getAccessToken());

        if (!accessToken.equals("")) {
            authorisedRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + accessToken)
                    .build();
        } else {
            authorisedRequest = originalRequest;
        }

        return chain.proceed(authorisedRequest);
    }
}
