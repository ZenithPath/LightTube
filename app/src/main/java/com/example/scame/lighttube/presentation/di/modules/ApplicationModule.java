package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.LightTubeApp;

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
    LightTubeApp provideApplication() {
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
