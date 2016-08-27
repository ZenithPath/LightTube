package com.example.scame.lighttube.data.repository;


import rx.Observable;

public interface ICategoriesDataManager {

    Observable<String> getCategoryId(String category);
}
