package com.example.scame.lighttube.presentation.di.modules;


import com.example.scame.lighttube.data.repository.RatingRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.GetRatingUseCase;
import com.example.scame.lighttube.domain.usecases.RateVideoUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.VideoRatingPresenter;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;
import com.example.scame.lighttube.presentation.presenters.VideoRatingPresenterImp;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.VideoRatingPresenter.PlayerView;

@Module
public class RatingModule {

    @Provides
    @PerActivity
    VideoRatingPresenter<PlayerView> provideRatingPresenter(GetRatingUseCase getRatingUseCase,
                                                            RateVideoUseCase rateVideoUseCase,
                                                            @Named("rating")SubscriptionsHandler subscriptionsHandler) {
        return new VideoRatingPresenterImp<>(getRatingUseCase, rateVideoUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    GetRatingUseCase provideRetrieveRatingUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                  RatingRepository ratingsDataManager) {
        return new GetRatingUseCase(subscribeOn, observeOn, ratingsDataManager);
    }

    @Provides
    @PerActivity
    RateVideoUseCase provideRateVideoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                             RatingRepository ratingsDataManager) {
        return new RateVideoUseCase(subscribeOn, observeOn, ratingsDataManager);
    }

    @Provides
    @Named("rating")
    @PerActivity
    SubscriptionsHandler provideRatingSubscriptionsHandler(GetRatingUseCase getRatingUseCase,
                                                           RateVideoUseCase rateVideoUseCase) {
        return new SubscriptionsHandler(getRatingUseCase, rateVideoUseCase);
    }
}
