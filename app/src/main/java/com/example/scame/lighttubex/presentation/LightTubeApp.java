package com.example.scame.lighttubex.presentation;

import android.app.Application;
import android.content.Context;

import com.example.scame.lighttubex.presentation.di.components.ApplicationComponent;
import com.example.scame.lighttubex.presentation.di.components.DaggerApplicationComponent;
import com.example.scame.lighttubex.presentation.di.modules.ApplicationModule;


public class LightTubeApp extends Application {

    private ApplicationComponent applicationComponent;

    public static LightTubeApp getApp(Context context) {
        return (LightTubeApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        buildAppComponent();
    }

    private void buildAppComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getAppComponent() {
        return applicationComponent;
    }
}
