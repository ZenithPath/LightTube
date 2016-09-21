package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.data.entities.content.ContentEntity;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public interface IContentDetailsDataManager {

    Observable<ContentEntity> getContentDetails(List<VideoModel> videoModels);
}
