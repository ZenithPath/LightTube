package com.example.scame.lighttube.data.repository;

import com.example.scame.lighttube.data.entities.TokenEntity;

import rx.Observable;

public interface IAccountDataManager {

    void saveTokens(TokenEntity tokenEntity, boolean saveRefreshToken);

    void refreshToken(TokenEntity tokenEntity);

    boolean isTokenExists();

    Observable<Void> signOut();

    Observable<TokenEntity> getTokenFromCache();

    // main method, above procedure used only locally and inside interceptors
    Observable<TokenEntity> getToken(String serverAuthCode);
}
