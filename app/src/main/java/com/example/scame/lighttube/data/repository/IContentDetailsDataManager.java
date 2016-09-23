package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public interface IContentDetailsDataManager {

    Observable<List<VideoModel>> getContentDetails(List<VideoModel> videoModels);
}
