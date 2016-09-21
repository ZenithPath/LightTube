package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.mappers.SearchListMapper;
import com.example.scame.lighttube.data.repository.ICategoryDataManager;
import com.example.scame.lighttube.data.repository.ISearchDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public class GridListUseCase extends UseCase<List<VideoModel>> {

    private ICategoryDataManager categoryDataManager;
    private ISearchDataManager searchDataManager;


    private String duration;
    private String category;
    private int page;

    public GridListUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                           ICategoryDataManager categoryDataManager,
                           ISearchDataManager searchDataManager) {
        super(subscribeOn, observeOn);

        this.categoryDataManager = categoryDataManager;
        this.searchDataManager = searchDataManager;
    }

    @Override
    protected Observable<List<VideoModel>> getUseCaseObservable() {
        SearchListMapper mapper = new SearchListMapper();

        return categoryDataManager.getCategoryId(category)
                .flatMap(categoryId -> searchDataManager.searchByCategory(categoryId, duration, page))
                .map(mapper::convert);
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
