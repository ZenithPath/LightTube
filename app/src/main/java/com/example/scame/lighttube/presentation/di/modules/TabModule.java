package com.example.scame.lighttube.presentation.di.modules;


import com.example.scame.lighttube.data.repository.IAccountDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.SignInCheckUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.navigation.Navigator;
import com.example.scame.lighttube.presentation.presenters.ITabActivityPresenter;
import com.example.scame.lighttube.presentation.presenters.TabActivityPresenterImp;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.ITabActivityPresenter.ITabActivityView;

@Module
public class TabModule {

    @PerActivity
    @Provides
    Navigator provideNavigator() {
        return new Navigator();
    }

    @PerActivity
    @Provides
    ITabActivityPresenter<ITabActivityView> provideTabActivityPresenter(SignInCheckUseCase useCase) {
        return new TabActivityPresenterImp<>(useCase);
    }

    @PerActivity
    @Provides
    SignInCheckUseCase provideSignInCheckUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                 IAccountDataManager dataManager) {

        return new SignInCheckUseCase(subscribeOn, observeOn, dataManager);
    }
}
