package com.example.scame.lighttube.presentation.di.components;


import android.app.Activity;

import com.example.scame.lighttube.presentation.LightTubeApp;
import com.example.scame.lighttube.presentation.di.modules.ChannelVideosModule;
import com.example.scame.lighttube.presentation.di.modules.GridModule;
import com.example.scame.lighttube.presentation.di.modules.RecentVideosModule;
import com.example.scame.lighttube.presentation.di.modules.SearchModule;
import com.example.scame.lighttube.presentation.di.modules.SignInModule;
import com.example.scame.lighttube.presentation.di.modules.VideoListModule;

public class ComponentsManager {

    private Activity activity;

    public ComponentsManager(Activity activity) {
        this.activity = activity;
    }

    public SearchComponent provideSearchComponent() {
        return DaggerSearchComponent.builder()
                .applicationComponent(LightTubeApp.getAppComponent())
                .searchModule(new SearchModule())
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

    public GridComponent provideGridComponent() {
        return DaggerGridComponent.builder()
                .applicationComponent(LightTubeApp.getAppComponent())
                .gridModule(new GridModule())
                .build();
    }

    public RecentVideosComponent provideRecentVideosComponent() {
        return DaggerRecentVideosComponent.builder()
                .applicationComponent(LightTubeApp.getAppComponent())
                .recentVideosModule(new RecentVideosModule())
                .build();
    }

    public ChannelVideosComponent provideChannelsComponent() {
        return DaggerChannelVideosComponent.builder()
                .applicationComponent(LightTubeApp.getAppComponent())
                .channelVideosModule(new ChannelVideosModule())
                .build();
    }
}
