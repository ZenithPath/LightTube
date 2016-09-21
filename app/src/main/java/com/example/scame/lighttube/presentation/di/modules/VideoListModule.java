package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.IContentDetailsDataManager;
import com.example.scame.lighttube.data.repository.IVideoListDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.ContentDetailsUseCase;
import com.example.scame.lighttube.domain.usecases.VideoListUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.IVideoListPresenter;
import com.example.scame.lighttube.presentation.presenters.VideoListPresenterImp;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.IVideoListPresenter.*;

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
    ContentDetailsUseCase provideContentDetailsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                       IContentDetailsDataManager dataManager) {

        return new ContentDetailsUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    IVideoListPresenter<VideoListView> provideVideoListPresenter(VideoListUseCase videosUseCase,
                                                                 ContentDetailsUseCase contentUseCase) {

        return new VideoListPresenterImp<>(videosUseCase, contentUseCase);
    }
}
