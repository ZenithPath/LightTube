package com.example.scame.lighttubex.presentation.di.components;


import android.app.Activity;

import com.example.scame.lighttubex.presentation.LightTubeApp;
import com.example.scame.lighttubex.presentation.di.modules.SearchModule;
import com.example.scame.lighttubex.presentation.di.modules.SignInModule;
import com.example.scame.lighttubex.presentation.di.modules.VideoListModule;

public class ComponentsManager {

    private Activity activity;

    public ComponentsManager(Activity activity) {
        this.activity = activity;
    }

    public SearchComponent provideSearchComponent() {
        return DaggerSearchComponent.builder()
                .applicationComponent(LightTubeApp.getAppComponent())
                .searchModule(new SearchModule(activity))
                .build();
    }

    public VideoListComponent provideVideoListComponent() {
        return DaggerVideoListComponent.builder()
                .applicationComponent(LightTubeApp.getAppComponent())
                .videoListModule(new VideoListModule())
                .build();
    }

    public SignInComponent provideSignInComponent() {
        return DaggerSignInComponent.builder()
                .applicationComponent(LightTubeApp.getAppComponent())
                .signInModule(new SignInModule(activity))
                .build();
    }
}
