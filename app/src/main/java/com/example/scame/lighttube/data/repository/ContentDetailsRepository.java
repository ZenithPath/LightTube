package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import rx.Observable;

public interface ContentDetailsRepository {

    Observable<VideoModelsWrapper> getContentDetails(VideoModelsWrapper modelsWrapper);
}
