package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.HomeVideosRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.GetHomeVideosUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.HomePresenter;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;
import com.example.scame.lighttube.presentation.presenters.HomePresenterImp;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.HomePresenter.VideoListView;

@Module
public class HomeModule {

    @PerActivity
    @Provides
    GetHomeVideosUseCase provideHomeVideosUseCase(HomeVideosRepository videoListDataManager,
                                                 SubscribeOn subscribeOn, ObserveOn observeOn) {
        return new GetHomeVideosUseCase(subscribeOn, observeOn, videoListDataManager);
    }

    @PerActivity
    @Provides
    SubscriptionsHandler provideSubscriptionsHandler(GetHomeVideosUseCase videosUseCase) {
        return new SubscriptionsHandler(videosUseCase);
    }

    @PerActivity
    @Provides
    HomePresenter<VideoListView> provideHomeVideosPresenter(GetHomeVideosUseCase videosUseCase,
                                                           SubscriptionsHandler subscriptionsHandler) {
        return new HomePresenterImp<>(videosUseCase, subscriptionsHandler);
    }
}
