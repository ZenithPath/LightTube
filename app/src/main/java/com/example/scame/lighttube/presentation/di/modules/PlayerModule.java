package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.IRatingDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.RateVideoUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveRatingUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.IPlayerPresenter;
import com.example.scame.lighttube.presentation.presenters.PlayerPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import dagger.Module;
import dagger.Provides;

@Module
public class PlayerModule {

    @Provides
    @PerActivity
    RetrieveRatingUseCase provideRetrieveRatingUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                       IRatingDataManager ratingsDataManager) {

        return new RetrieveRatingUseCase(subscribeOn, observeOn, ratingsDataManager);
    }

    @Provides
    @PerActivity
    RateVideoUseCase provideRateVideoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                             IRatingDataManager ratingsDataManager) {

        return new RateVideoUseCase(subscribeOn, observeOn, ratingsDataManager);
    }

    @Provides
    @PerActivity
    IPlayerPresenter<IPlayerPresenter.PlayerView> providePlayerPresenter(RetrieveRatingUseCase retrieveRatingUseCase,
                                                                         RateVideoUseCase rateVideoUseCase,
                                                                         SubscriptionsHandler subscriptionsHandler) {

        return new PlayerPresenterImp<>(retrieveRatingUseCase, rateVideoUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    SubscriptionsHandler provideSubscriptionsHandler(RetrieveRatingUseCase retrieveRatingUseCase,
                                                     RateVideoUseCase rateVideoUseCase) {

        return new SubscriptionsHandler(retrieveRatingUseCase, rateVideoUseCase);
    }
}
