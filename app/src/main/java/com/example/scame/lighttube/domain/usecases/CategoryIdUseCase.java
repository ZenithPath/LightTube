package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.ICategoriesDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import rx.Observable;

public class CategoryIdUseCase extends UseCase<String> {

    private ICategoriesDataManager dataManager;

    private String category;

    public CategoryIdUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                             ICategoriesDataManager dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<String> getUseCaseObservable() {
        return dataManager.getCategoryId(category);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
