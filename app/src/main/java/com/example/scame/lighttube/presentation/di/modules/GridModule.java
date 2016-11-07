package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.CategoryRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.GetGridVideosUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.GridPresenter;
import com.example.scame.lighttube.presentation.presenters.GridPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import dagger.Module;
import dagger.Provides;

@Module
public class GridModule {

    @PerActivity
    @Provides
    GridPresenter<GridPresenter.GridView> provideGridPresenter(GetGridVideosUseCase useCase, SubscriptionsHandler handler) {
        return new GridPresenterImp<>(useCase, handler);
    }

    @PerActivity
    @Provides
    SubscriptionsHandler provideSubscriptionsHandler(GetGridVideosUseCase gridUseCase) {
        return new SubscriptionsHandler(gridUseCase);
    }

    @PerActivity
    @Provides
    GetGridVideosUseCase provideGridUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, CategoryRepository categoryRepository) {
        return new GetGridVideosUseCase(subscribeOn, observeOn, categoryRepository);
    }
}
