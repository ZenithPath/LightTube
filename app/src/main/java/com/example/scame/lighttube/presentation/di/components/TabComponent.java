package com.example.scame.lighttube.presentation.di.components;


import com.example.scame.lighttube.presentation.activities.TabActivity;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.di.modules.TabModule;

import dagger.Component;

@PerActivity
@Component(modules = TabModule.class, dependencies = ApplicationComponent.class)
public interface TabComponent {

    void inject(TabActivity activity);
}
