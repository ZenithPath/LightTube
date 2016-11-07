package com.example.scame.lighttube.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

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

public class AccountRepositoryImp implements AccountRepository {

    private SharedPreferences sharedPrefs;

    private TokenApi tokenApi;

    private String accessTokenKey;
    private String refreshTokenKey;
    private String userChannelKey;

    public AccountRepositoryImp(SharedPreferences sharedPrefs, Context context) {
        this.sharedPrefs = sharedPrefs;

        accessTokenKey = context.getString(R.string.access_token);
        refreshTokenKey = context.getString(R.string.refresh_token);
        userChannelKey = context.getString(R.string.channel_url_key);
    }

    @Override
    public Observable<TokenEntity> getToken(String serverAuthCode) {
        return isTokenExists() ? getTokenFromCache() : getTokenFromNetwork(serverAuthCode);
    }

    @Override
    public void saveTokens(TokenEntity tokenEntity, boolean saveRefreshedToken) {
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString(accessTokenKey, tokenEntity.getAccessToken());
        if (saveRefreshedToken) {
            editor.putString(refreshTokenKey, tokenEntity.getRefreshToken());
        }
        editor.apply();
    }

    @Override
    public void refreshToken(TokenEntity tokenEntity) {
        TokenEntity newEntity = new TokenEntity();

        try {
            newEntity = getTokenApi()
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
        return Observable.defer(() -> {
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(accessTokenKey, "");
            editor.putString(refreshTokenKey, "");
            editor.putString(userChannelKey, "");
            editor.apply();

            return Observable.empty();
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
    public Observable<TokenEntity> getTokenFromCache() {
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setRefreshToken(sharedPrefs.getString(refreshTokenKey, ""));
        tokenEntity.setAccessToken(sharedPrefs.getString(accessTokenKey, ""));

        return Observable.just(tokenEntity);
    }


    private Observable<TokenEntity> getTokenFromNetwork(String authServerCode) {
        return getTokenApi().getAccessToken(buildRequestBody(authServerCode))
                .doOnNext(tokenEntity -> saveTokens(tokenEntity, true));
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
    public boolean isTokenExists() {
        return !sharedPrefs.getString(accessTokenKey, "").equals("");
    }

    // to avoid circular dependency (AccountManager -> TokenApi -> Retrofit -> OkHttp -> Interceptors -> AccountManager)
    // evidently, it's a design problem, so should be fixed // FIXME: 9/23/16
    private TokenApi getTokenApi() {
        if (tokenApi == null) {
            Retrofit retrofit = LightTubeApp.getAppComponent().getRetrofit();
            tokenApi = retrofit.create(TokenApi.class);
        }

        return tokenApi;
    }
}