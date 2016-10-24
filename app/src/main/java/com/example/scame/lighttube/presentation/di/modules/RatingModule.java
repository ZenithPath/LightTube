package com.example.scame.lighttube.presentation.di.modules;


import com.example.scame.lighttube.data.repository.IRatingDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.RateVideoUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveRatingUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.IVideoRatingPresenter;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;
import com.example.scame.lighttube.presentation.presenters.VideoRatingPresenterImp;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.IVideoRatingPresenter.PlayerView;

@Module
public class RatingModule {

    @Provides
    @PerActivity
    IVideoRatingPresenter<PlayerView> provideRatingPresenter(RetrieveRatingUseCase retrieveRatingUseCase,
                                                             RateVideoUseCase rateVideoUseCase,
                                                             @Named("rating")SubscriptionsHandler subscriptionsHandler) {
        return new VideoRatingPresenterImp<>(retrieveRatingUseCase, rateVideoUseCase, subscriptionsHandler);
    }

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
    @Named("rating")
    @PerActivity
    SubscriptionsHandler provideRatingSubscriptionsHandler(RetrieveRatingUseCase retrieveRatingUseCase,
                                                           RateVideoUseCase rateVideoUseCase) {
        return new SubscriptionsHandler(retrieveRatingUseCase, rateVideoUseCase);
    }
}
