package com.example.scame.lighttube.presentation.di.components;


import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.di.modules.HomeModule;
import com.example.scame.lighttube.presentation.fragments.HomeFragment;

import dagger.Component;

@PerActivity
@Component(modules = HomeModule.class, dependencies = ApplicationComponent.class)
public interface HomeComponent {

    void inject(HomeFragment fragment);
}
