package com.example.scame.lighttubex.presentation.di.components;

import com.example.scame.lighttubex.data.di.DataModule;
import com.example.scame.lighttubex.data.repository.SharedPrefsManager;
import com.example.scame.lighttubex.presentation.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    SharedPrefsManager getSharedPrefsManager();

    Retrofit getRetrofit();
}
