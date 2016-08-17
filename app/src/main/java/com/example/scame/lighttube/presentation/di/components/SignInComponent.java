package com.example.scame.lighttube.presentation.di.components;


import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.di.modules.SignInModule;
import com.example.scame.lighttube.presentation.fragments.SignInFragment;

import dagger.Component;

@PerActivity
@Component(modules = SignInModule.class, dependencies = ApplicationComponent.class)
public interface SignInComponent {

    void inject(SignInFragment fragment);
}
