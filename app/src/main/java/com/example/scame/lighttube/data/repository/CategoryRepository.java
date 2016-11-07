package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import rx.Observable;

public interface CategoryRepository {

    Observable<VideoModelsWrapper> getVideosByCategory(String category, String duration, int page);
}
