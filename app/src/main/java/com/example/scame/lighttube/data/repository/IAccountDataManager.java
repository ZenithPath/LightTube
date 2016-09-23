package com.example.scame.lighttube.data.repository;

import com.example.scame.lighttube.data.entities.TokenEntity;

import rx.Observable;

public interface IAccountDataManager {

    void saveTokens(TokenEntity tokenEntity, boolean saveRefreshToken);

    void refreshToken(TokenEntity tokenEntity);

    boolean ifTokenExists();

    Observable<Void> signOut();

    Observable<TokenEntity> getTokenEntity();

    // main method, entity used only by interceptor
    Observable<TokenEntity> getToken(String serverAuthCode);
}
