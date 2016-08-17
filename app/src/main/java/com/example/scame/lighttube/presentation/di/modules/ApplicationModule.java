package com.example.scame.lighttube.presentation.di.modules;

import android.app.Application;

import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.LightTubeApp;
import com.example.scame.lighttube.presentation.navigation.Navigator;

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
    Application provideApplication() {
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

    @Singleton
    @Provides
    Navigator provideNavigator() {
        return new Navigator();
    }
}
