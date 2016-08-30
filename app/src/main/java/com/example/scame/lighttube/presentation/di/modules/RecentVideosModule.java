package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.IRecentVideosPresenter;
import com.example.scame.lighttube.presentation.presenters.RecentVideosPresenterImp;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.IRecentVideosPresenter.RecentVideosView;

@Module
public class RecentVideosModule {

    @PerActivity
    @Provides
    IRecentVideosPresenter<RecentVideosView> provideRecentVideosPresenter() {
        return new RecentVideosPresenterImp<>();
    }
}
