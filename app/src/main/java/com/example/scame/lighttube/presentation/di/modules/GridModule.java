package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.ICategoryDataManager;
import com.example.scame.lighttube.data.repository.ISearchDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.GridListUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.GridPresenterImp;
import com.example.scame.lighttube.presentation.presenters.IGridPresenter;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import dagger.Module;
import dagger.Provides;

@Module
public class GridModule {

    @PerActivity
    @Provides
    IGridPresenter<IGridPresenter.GridView> provideGridPresenter(GridListUseCase useCase, SubscriptionsHandler handler) {
        return new GridPresenterImp<>(useCase, handler);
    }

    @PerActivity
    @Provides
    SubscriptionsHandler provideSubscriptionsHandler(GridListUseCase gridUseCase) {
        return new SubscriptionsHandler(gridUseCase);
    }

    @PerActivity
    @Provides
    GridListUseCase provideGridUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                       ISearchDataManager searchDataManager,
                                       ICategoryDataManager categoryDataManager) {

        return new GridListUseCase(subscribeOn, observeOn, categoryDataManager, searchDataManager);
    }
}
