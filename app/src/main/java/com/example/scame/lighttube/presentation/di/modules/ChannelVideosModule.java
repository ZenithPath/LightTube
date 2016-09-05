package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.IRecentVideosDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.ChannelVideosUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.ChannelsPresenterImp;
import com.example.scame.lighttube.presentation.presenters.IChannelsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ChannelVideosModule {

    @Provides
    @PerActivity
    ChannelVideosUseCase provideChannelsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                IRecentVideosDataManager dataManager) {

        return new ChannelVideosUseCase(subscribeOn, observeOn, dataManager);
    }

    @Provides
    @PerActivity
    IChannelsPresenter<IChannelsPresenter.ChannelsView> provideChannelsPresenter(ChannelVideosUseCase useCase) {
        return new ChannelsPresenterImp<>(useCase);
    }
}
