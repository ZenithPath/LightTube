package com.example.scame.lighttubex.presentation.di.modules;

import android.app.Activity;

import dagger.Module;

@Module
public class VideoListModule {

    private Activity activity;

    public VideoListModule(Activity activity) {
        this.activity = activity;
    }
}
