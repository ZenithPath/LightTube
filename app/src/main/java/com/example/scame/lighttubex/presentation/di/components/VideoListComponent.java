package com.example.scame.lighttubex.presentation.di.components;


import com.example.scame.lighttubex.presentation.activities.TabActivity;
import com.example.scame.lighttubex.presentation.di.PerActivity;
import com.example.scame.lighttubex.presentation.di.modules.VideoListModule;
import com.example.scame.lighttubex.presentation.fragments.VideoListFragment;

import dagger.Component;

@PerActivity
@Component(modules = VideoListModule.class, dependencies = ApplicationComponent.class)
public interface VideoListComponent {

    void inject(TabActivity activity);

    void inject(VideoListFragment fragment);
}
