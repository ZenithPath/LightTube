package com.example.scame.lighttube.data.repository;


import rx.Observable;

public interface IUserChannelDataManager {

    Observable<String> getUserChannelUrl();
}
