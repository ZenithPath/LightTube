package com.example.scame.lighttube.data.repository;


import rx.Observable;

public interface ICategoryDataManager {

    Observable<String> getCategoryId(String category);
}
