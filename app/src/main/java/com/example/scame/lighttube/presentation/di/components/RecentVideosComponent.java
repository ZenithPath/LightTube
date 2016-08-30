package com.example.scame.lighttube.presentation.di.components;

import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.di.modules.RecentVideosModule;
import com.example.scame.lighttube.presentation.fragments.RecentVideosFragment;

import dagger.Component;

@PerActivity
@Component(modules = RecentVideosModule.class, dependencies = ApplicationComponent.class)
public interface RecentVideosComponent {

    void inject(RecentVideosFragment fragment);
}
