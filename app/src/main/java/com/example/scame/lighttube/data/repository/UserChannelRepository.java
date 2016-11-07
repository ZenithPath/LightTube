package com.example.scame.lighttube.data.repository;


import rx.Observable;

public interface UserChannelRepository {

    Observable<String> getUserChannelUrl();
}
