package com.example.scame.lighttube.presentation.di.components;


import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.di.modules.ChannelVideosModule;
import com.example.scame.lighttube.presentation.fragments.ChannelVideosFragment;

import dagger.Component;

@PerActivity
@Component(modules = ChannelVideosModule.class, dependencies = ApplicationComponent.class)
public interface ChannelVideosComponent {

    void inject(ChannelVideosFragment channelVideosFragment);
}
