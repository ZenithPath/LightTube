package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.ICategoryDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public class GridListUseCase extends UseCase<List<VideoModel>> {

    private ICategoryDataManager categoryDataManager;

    private String duration;
    private String category;
    private int page;

    public GridListUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ICategoryDataManager categoryDataManager) {
        super(subscribeOn, observeOn);

        this.categoryDataManager = categoryDataManager;
    }

    @Override
    protected Observable<List<VideoModel>> getUseCaseObservable() {
        return categoryDataManager.getVideosByCategory(category, duration, page);
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
