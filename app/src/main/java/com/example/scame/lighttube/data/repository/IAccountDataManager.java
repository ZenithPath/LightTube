package com.example.scame.lighttube.data.repository;

import com.example.scame.lighttube.data.entities.TokenEntity;

import rx.Observable;

public interface IAccountDataManager {

    void saveTokens(TokenEntity tokenEntity, boolean saveRefreshToken);

    void refreshToken(TokenEntity tokenEntity);

    boolean ifTokenExists();

    Observable<Void> signOut();

    Observable<TokenEntity> getTokenEntity();

    Observable<TokenEntity> fetchWithServerAuthCode(String authServerCode);
}
