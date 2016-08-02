package com.example.scame.lighttubex.presentation.di.modules;

import android.app.Activity;

import com.example.scame.lighttubex.data.repository.IVideoListDataManager;
import com.example.scame.lighttubex.domain.schedulers.ObserveOn;
import com.example.scame.lighttubex.domain.schedulers.SubscribeOn;
import com.example.scame.lighttubex.domain.usecases.VideoListUseCase;
import com.example.scame.lighttubex.presentation.di.PerActivity;
import com.example.scame.lighttubex.presentation.presenters.IVideoListPresenter;
import com.example.scame.lighttubex.presentation.presenters.VideoListPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class VideoListModule {

    private Activity activity;

    public VideoListModule(Activity activity) {
        this.activity = activity;
    }

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
