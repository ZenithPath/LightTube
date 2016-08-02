package com.example.scame.lighttubex.data.repository;


import com.example.scame.lighttubex.presentation.model.VideoItemModel;

import java.util.List;

import rx.Observable;

public interface IVideoListDataManager {

    Observable<List<VideoItemModel>> getVideoItemsList();
}
