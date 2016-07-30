package com.example.scame.lighttubex.presentation.di.components;

import android.app.Application;

import com.example.scame.lighttubex.data.di.DataModule;
import com.example.scame.lighttubex.data.repository.ISignInDataManager;
import com.example.scame.lighttubex.domain.schedulers.ObserveOn;
import com.example.scame.lighttubex.domain.schedulers.SubscribeOn;
import com.example.scame.lighttubex.presentation.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    Application getApp();

    Retrofit getRetrofit();

    ObserveOn getObserveOn();

    SubscribeOn getSubscribeOn();

    ISignInDataManager getSignInDataManager();
}
