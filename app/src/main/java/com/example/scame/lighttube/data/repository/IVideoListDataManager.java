package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.VideoItemModel;

import java.util.List;

import rx.Observable;

public interface IVideoListDataManager {

    Observable<List<VideoItemModel>> getVideoItemsList(int page);
}
