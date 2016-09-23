package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public interface ICategoryDataManager {

    Observable<List<VideoModel>> getVideosByCategory(String category, String duration, int page);
}
