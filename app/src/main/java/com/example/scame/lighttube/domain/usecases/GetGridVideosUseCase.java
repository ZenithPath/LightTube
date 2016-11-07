package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.CategoryRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import rx.Observable;

public class GetGridVideosUseCase extends UseCase<VideoModelsWrapper> {

    private CategoryRepository categoryRepository;

    private String duration;
    private String category;
    private int page;

    public GetGridVideosUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, CategoryRepository categoryRepository) {
        super(subscribeOn, observeOn);
        this.categoryRepository = categoryRepository;
    }

    @Override
    protected Observable<VideoModelsWrapper> getUseCaseObservable() {
        return categoryRepository.getVideosByCategory(category, duration, page);
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

    public String getDuration() {
        return duration;
    }

    public String getCategory() {
        return category;
    }

    public int getPage() {
        return page;
    }
}
