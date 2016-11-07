package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.ChannelVideosRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.GetChannelVideosUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.ChannelVideosPresenter;
import com.example.scame.lighttube.presentation.presenters.ChannelVideosPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import dagger.Module;
import dagger.Provides;

@Module
public class ChannelVideosModule {

    @Provides
    @PerActivity
    GetChannelVideosUseCase provideChannelsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                   ChannelVideosRepository videosDataManager) {
        return new GetChannelVideosUseCase(subscribeOn, observeOn, videosDataManager);
    }

    @Provides
    @PerActivity
    SubscriptionsHandler provideSubscriptionsHandler(GetChannelVideosUseCase videosUseCase) {
        return new SubscriptionsHandler(videosUseCase);
    }

    @Provides
    @PerActivity
    ChannelVideosPresenter<ChannelVideosPresenter.ChannelsView> provideChannelsPresenter(GetChannelVideosUseCase videosUseCase,
                                                                                         SubscriptionsHandler handler) {
        return new ChannelVideosPresenterImp<>(videosUseCase, handler);
    }
}
