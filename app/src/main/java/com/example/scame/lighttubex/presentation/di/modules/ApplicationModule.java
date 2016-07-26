package com.example.scame.lighttubex.presentation.di.modules;

import android.content.Context;

import com.example.scame.lighttubex.domain.schedulers.ObserveOn;
import com.example.scame.lighttubex.domain.schedulers.SubscribeOn;
import com.example.scame.lighttubex.presentation.LightTubeApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class ApplicationModule {

    private LightTubeApp lightTubeApp;

    public ApplicationModule(LightTubeApp lightTubeApp) {
        this.lightTubeApp = lightTubeApp;
    }

    @Singleton
    @Provides
    Context provideAppContext() {
        return lightTubeApp;
    }

    @Singleton
    @Provides
    ObserveOn provideObserveOn() {
        return AndroidSchedulers::mainThread;
    }

    @Singleton
    @Provides
    SubscribeOn provideSubscribeOn() {
        return Schedulers::newThread;
    }
}
