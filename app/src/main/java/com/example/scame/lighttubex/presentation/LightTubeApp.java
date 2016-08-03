package com.example.scame.lighttubex.presentation;

import android.app.Application;
import android.content.Context;

import com.example.scame.lighttubex.data.di.DataModule;
import com.example.scame.lighttubex.presentation.di.components.ApplicationComponent;
import com.example.scame.lighttubex.presentation.di.components.DaggerApplicationComponent;
import com.example.scame.lighttubex.presentation.di.modules.ApplicationModule;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;


public class LightTubeApp extends Application {

    private static ApplicationComponent applicationComponent;

    public static LightTubeApp getApp(Context context) {
        return (LightTubeApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        enablePicassoCache();
        buildAppComponent();
    }

    private void buildAppComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule())
                .build();
    }

    private void enablePicassoCache() {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }

    public static ApplicationComponent getAppComponent() {
        return applicationComponent;
    }
}
