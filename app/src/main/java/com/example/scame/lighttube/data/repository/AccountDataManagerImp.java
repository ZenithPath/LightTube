package com.example.scame.lighttube.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.entities.TokenEntity;
import com.example.scame.lighttube.data.rest.TokenApi;
import com.example.scame.lighttube.presentation.LightTubeApp;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;

public class AccountDataManagerImp implements IAccountDataManager {

    private Retrofit retrofit;

    private SharedPreferences sp;

    private String accessTokenKey;
    private String refreshTokenKey;

    public AccountDataManagerImp() {
        Context context = LightTubeApp.getAppComponent().getApp();
        retrofit = LightTubeApp.getAppComponent().getRetrofit();
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        accessTokenKey = context.getString(R.string.access_token);
        refreshTokenKey = context.getString(R.string.refresh_token);
    }

    @Override
    public void saveTokens(TokenEntity tokenEntity, boolean saveRefreshToken) {
        SharedPreferences.Editor editor = sp.edit();
        Log.i("savedToken: ", tokenEntity.getAccessToken());
        editor.putString(accessTokenKey, tokenEntity.getAccessToken());
        if (saveRefreshToken) {
            editor.putString(refreshTokenKey, tokenEntity.getRefreshToken());
        }
        editor.apply();
    }

    @Override
    public void refreshToken(TokenEntity tokenEntity) {
        TokenApi tokenApi = retrofit.create(TokenApi.class);
        TokenEntity newEntity = new TokenEntity();
        try {
            newEntity = tokenApi
                    .getRefreshedToken(buildRefreshRequestBody(tokenEntity.getRefreshToken()))
                    .execute()
                    .body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveTokens(newEntity, false);
    }

    @Override
    public Observable<Void> signOut() {
        return Observable.create((Observable.OnSubscribe<Void>) subscriber -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(accessTokenKey, "");
            editor.putString(refreshTokenKey, "");
            editor.apply();

            subscriber.onCompleted();
        });
    }

    private Map<String, String> buildRefreshRequestBody(String refreshToken) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("client_id", PrivateValues.CLIENT_ID);
        params.put("client_secret", PrivateValues.SECRET_KEY);
        params.put("refresh_token", refreshToken);
        params.put("grant_type", "refresh_token");

        return params;
    }

    @Override
    public Observable<TokenEntity> getTokenEntity() {
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setRefreshToken(sp.getString(refreshTokenKey, ""));
        tokenEntity.setAccessToken(sp.getString(accessTokenKey, ""));

        return Observable.just(tokenEntity);
    }

    @Override
    public Observable<TokenEntity> fetchWithServerAuthCode(String authServerCode) {
        TokenApi tokenApi = retrofit.create(TokenApi.class);
        return tokenApi.getAccessToken(buildRequestBody(authServerCode));
    }

    private Map<String, String> buildRequestBody(String authServerCode) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("code", authServerCode);
        params.put("client_id", PrivateValues.CLIENT_ID);
        params.put("grant_type", "authorization_code");
        params.put("client_secret", PrivateValues.SECRET_KEY);
        params.put("redirect_uri","");

        return params;
    }

    @Override
    public boolean ifTokenExists() {
        return !sp.getString(accessTokenKey, "").equals("");
    }
}
