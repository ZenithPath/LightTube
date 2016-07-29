package com.example.scame.lighttubex.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.scame.lighttubex.PrivateValues;
import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.data.enteties.TokenEntity;
import com.example.scame.lighttubex.data.rest.TokenApi;
import com.example.scame.lighttubex.presentation.LightTubeApp;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;

public class SignInDataManagerImp implements  ISignInDataManager {

    private Retrofit retrofit;

    private SharedPreferences sp;

    private String accessTokenKey;
    private String refreshTokenKey;

    public SignInDataManagerImp() {
        Context context = LightTubeApp.getAppComponent().getApp();
        retrofit = LightTubeApp.getAppComponent().getRetrofit();
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        accessTokenKey = context.getString(R.string.access_token);
        refreshTokenKey = context.getString(R.string.refresh_token);
    }

    @Override
    public void saveTokens(TokenEntity tokenEntity) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(accessTokenKey, tokenEntity.getAccessToken());
        editor.putString(refreshTokenKey, tokenEntity.getRefreshToken());
        editor.apply();
    }

    @Override
    public void refreshToken(TokenEntity tokenEntity) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(accessTokenKey, tokenEntity.getAccessToken());
        editor.apply();
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
