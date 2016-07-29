package com.example.scame.lighttubex.data.repository;

import com.example.scame.lighttubex.data.enteties.TokenEntity;

import rx.Observable;

public interface ISignInDataManager {

    void saveTokens(TokenEntity tokenEntity);

    void refreshToken(TokenEntity tokenEntity);

    boolean ifTokenExists();

    Observable<TokenEntity> getTokenEntity();

    Observable<TokenEntity> fetchWithServerAuthCode(String authServerCode);
}
