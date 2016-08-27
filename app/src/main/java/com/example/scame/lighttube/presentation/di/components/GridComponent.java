package com.example.scame.lighttube.presentation.di.components;

import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.di.modules.GridModule;
import com.example.scame.lighttube.presentation.fragments.GridFragment;

import dagger.Component;

@PerActivity
@Component(modules = GridModule.class, dependencies = ApplicationComponent.class)
public interface GridComponent {

    void inject(GridFragment gridFragment);
}
