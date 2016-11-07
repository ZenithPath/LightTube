package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import rx.Observable;

public interface HomeVideosRepository {

    Observable<VideoModelsWrapper> getVideoItemsList(int page);
}
