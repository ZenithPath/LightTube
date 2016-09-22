package com.example.scame.lighttube.presentation.di.modules;


import com.example.scame.lighttube.data.repository.IAccountDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.CheckLoginUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.navigation.Navigator;
import com.example.scame.lighttube.presentation.presenters.ITabActivityPresenter;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;
import com.example.scame.lighttube.presentation.presenters.TabActivityPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class TabModule {

    @PerActivity
    @Provides
    Navigator provideNavigator() {
        return new Navigator();
    }

    @PerActivity
    @Provides
    CheckLoginUseCase provideSignInCheckUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                IAccountDataManager dataManager) {

        return new CheckLoginUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    SubscriptionsHandler provideSubscriptionsHandler(CheckLoginUseCase checkLoginUseCase) {
        return new SubscriptionsHandler(checkLoginUseCase);
    }

    @PerActivity
    @Provides
    ITabActivityPresenter<ITabActivityPresenter.ITabActivityView> provideTabActivityPresenter(CheckLoginUseCase useCase,
                                                                                              SubscriptionsHandler handler) {
        return new TabActivityPresenterImp<>(useCase, handler);
    }
}
