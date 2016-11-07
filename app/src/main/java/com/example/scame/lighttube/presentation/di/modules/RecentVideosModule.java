package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.RecentVideosRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.GetRecentVideosUseCase;
import com.example.scame.lighttube.domain.usecases.GetSubscriptionsUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.RecentVideosPresenter;
import com.example.scame.lighttube.presentation.presenters.RecentVideosPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.RecentVideosPresenter.RecentVideosView;

@Module
public class RecentVideosModule {

    @PerActivity
    @Provides
    RecentVideosPresenter<RecentVideosView> provideRecentVideosPresenter(GetRecentVideosUseCase getRecentVideosUseCase,
                                                                         GetSubscriptionsUseCase subscriptionsUseCase,
                                                                         SubscriptionsHandler subscriptionsHandler) {
        return new RecentVideosPresenterImp<>(getRecentVideosUseCase, subscriptionsUseCase, subscriptionsHandler);
    }

    @PerActivity
    @Provides
    SubscriptionsHandler provideSubscriptionsHandler(GetSubscriptionsUseCase subscriptionsUseCase,
                                                     GetRecentVideosUseCase getRecentVideosUseCase) {
        return new SubscriptionsHandler(subscriptionsUseCase, getRecentVideosUseCase);
    }

   @PerActivity
   @Provides
   GetSubscriptionsUseCase provideSubscriptionsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                       RecentVideosRepository recentVideosRepository) {
       return new GetSubscriptionsUseCase(subscribeOn, observeOn, recentVideosRepository);
   }

    @PerActivity
    @Provides
    GetRecentVideosUseCase provideRecentVideosUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                      RecentVideosRepository dataManager) {
        return new GetRecentVideosUseCase(subscribeOn, observeOn, dataManager);
    }
}
