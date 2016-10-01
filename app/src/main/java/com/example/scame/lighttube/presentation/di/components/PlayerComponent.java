package com.example.scame.lighttube.presentation.di.components;


import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.di.modules.PlayerModule;

import dagger.Component;

@PerActivity
@Component(modules = PlayerModule.class, dependencies = ApplicationComponent.class)
public interface PlayerComponent {

    void inject(PlayerActivity playerActivity);
}
