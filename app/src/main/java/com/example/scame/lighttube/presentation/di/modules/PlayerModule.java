package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.data.repository.IRatingDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.RateVideoUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveCommentsUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveRatingUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.CommentsPresenterImp;
import com.example.scame.lighttube.presentation.presenters.ICommentsPresenter;
import com.example.scame.lighttube.presentation.presenters.IPlayerHeaderPresenter;
import com.example.scame.lighttube.presentation.presenters.PlayerHeaderPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.ICommentsPresenter.*;
import static com.example.scame.lighttube.presentation.presenters.IPlayerHeaderPresenter.*;

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
    RetrieveCommentsUseCase provideRetrieveCommentsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                           ICommentsDataManager commentsDataManager) {

        return new RetrieveCommentsUseCase(subscribeOn, observeOn, commentsDataManager);
    }

    @Provides
    @PerActivity
    IPlayerHeaderPresenter<PlayerView> providePlayerPresenter(RetrieveRatingUseCase retrieveRatingUseCase,
                                                              RateVideoUseCase rateVideoUseCase,
                                                              @Named("header")SubscriptionsHandler subscriptionsHandler) {

        return new PlayerHeaderPresenterImp<>(retrieveRatingUseCase, rateVideoUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    ICommentsPresenter<CommentsView> provideCommentsPresenter(RetrieveCommentsUseCase retrieveCommentsUseCase,
                                                              @Named("comments")SubscriptionsHandler subscriptionsHandler) {

        return new CommentsPresenterImp<>(retrieveCommentsUseCase, subscriptionsHandler);
    }

    @Provides
    @Named("header")
    @PerActivity
    SubscriptionsHandler provideHeaderSubscriptionsHandler(RetrieveRatingUseCase retrieveRatingUseCase,
                                                     RateVideoUseCase rateVideoUseCase) {

        return new SubscriptionsHandler(retrieveRatingUseCase, rateVideoUseCase);
    }

    @Provides
    @Named("comments")
    @PerActivity
    SubscriptionsHandler provideCommentsSubscriptionsHandler(RetrieveCommentsUseCase retrieveCommentsUseCase) {
        return new SubscriptionsHandler(retrieveCommentsUseCase);
    }
}
