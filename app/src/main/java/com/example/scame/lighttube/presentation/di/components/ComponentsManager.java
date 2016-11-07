package com.example.scame.lighttube.presentation.di.components;


import android.app.Activity;

import com.example.scame.lighttube.presentation.LightTubeApp;
import com.example.scame.lighttube.presentation.di.modules.ChannelVideosModule;
import com.example.scame.lighttube.presentation.di.modules.GridModule;
import com.example.scame.lighttube.presentation.di.modules.PlayerFooterModule;
import com.example.scame.lighttube.presentation.di.modules.RecentVideosModule;
import com.example.scame.lighttube.presentation.di.modules.RepliesModule;
import com.example.scame.lighttube.presentation.di.modules.SearchModule;
import com.example.scame.lighttube.presentation.di.modules.SignInModule;
import com.example.scame.lighttube.presentation.di.modules.HomeModule;

public class ComponentsManager {

    private Activity activity;

    private SearchComponent searchComponent;

    private HomeComponent homeComponent;

    private SignInComponent signInComponent;

    private GridComponent gridComponent;

    private RecentVideosComponent recentVideosComponent;

    private ChannelVideosComponent channelVideosComponent;

    private PlayerFooterComponent playerFooterComponent;

    private RepliesComponent repliesComponent;

    public ComponentsManager(Activity activity) {
        this.activity = activity;
    }

    public SearchComponent provideSearchComponent() {
        if (searchComponent == null) {
            searchComponent = DaggerSearchComponent.builder()
                    .applicationComponent(LightTubeApp.getAppComponent())
                    .searchModule(new SearchModule())
                    .build();
        }
        return searchComponent;
    }

    public HomeComponent provideVideoListComponent() {
        if (homeComponent == null) {
            homeComponent = DaggerHomeComponent.builder()
                    .applicationComponent(LightTubeApp.getAppComponent())
                    .homeModule(new HomeModule())
                    .build();
        }

        return homeComponent;
    }

    public SignInComponent provideSignInComponent() {
        if (signInComponent == null) {
            signInComponent = DaggerSignInComponent.builder()
                    .applicationComponent(LightTubeApp.getAppComponent())
                    .signInModule(new SignInModule(activity))
                    .build();
        }
        return signInComponent;
    }

    public GridComponent provideGridComponent() {
        if (gridComponent == null) {
            gridComponent = DaggerGridComponent.builder()
                    .applicationComponent(LightTubeApp.getAppComponent())
                    .gridModule(new GridModule())
                    .build();
        }
        return gridComponent;
    }

    public RecentVideosComponent provideRecentVideosComponent() {
        if (recentVideosComponent == null) {
            recentVideosComponent = DaggerRecentVideosComponent.builder()
                    .applicationComponent(LightTubeApp.getAppComponent())
                    .recentVideosModule(new RecentVideosModule())
                    .build();
        }
        return recentVideosComponent;
    }

    public ChannelVideosComponent provideChannelsComponent() {
        if (channelVideosComponent == null) {
            channelVideosComponent = DaggerChannelVideosComponent.builder()
                    .applicationComponent(LightTubeApp.getAppComponent())
                    .channelVideosModule(new ChannelVideosModule())
                    .build();
        }
        return channelVideosComponent;
    }

    public PlayerFooterComponent providePlayerFooterComponent() {
        if (playerFooterComponent == null) {
            playerFooterComponent = DaggerPlayerFooterComponent.builder()
                    .applicationComponent(LightTubeApp.getAppComponent())
                    .playerFooterModule(new PlayerFooterModule())
                    .build();
        }
        return playerFooterComponent;
    }

    public RepliesComponent provideRepliesComponent() {
        if (repliesComponent == null) {
            repliesComponent = DaggerRepliesComponent.builder()
                    .applicationComponent(LightTubeApp.getAppComponent())
                    .repliesModule(new RepliesModule())
                    .build();
        }
        return repliesComponent;
    }
}
