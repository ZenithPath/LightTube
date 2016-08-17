package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.IVideoListDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.VideoListUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.IVideoListPresenter;
import com.example.scame.lighttube.presentation.presenters.VideoListPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class VideoListModule {

    @PerActivity
    @Provides
    VideoListUseCase provideVideoListUseCase(IVideoListDataManager dataManager,
                                             SubscribeOn subscribeOn, ObserveOn observeOn) {

        return new VideoListUseCase(dataManager, subscribeOn, observeOn);
    }

    @PerActivity
    @Provides
    IVideoListPresenter<IVideoListPresenter.VideoListView> provideVideoListPresenter(VideoListUseCase useCase) {
        return new VideoListPresenterImp<>(useCase);
    }
}
