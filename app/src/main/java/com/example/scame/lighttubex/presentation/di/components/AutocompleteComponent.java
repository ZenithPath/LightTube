package com.example.scame.lighttubex.presentation.di.components;


import com.example.scame.lighttubex.presentation.activities.AutocompleteActivity;
import com.example.scame.lighttubex.presentation.di.PerActivity;
import com.example.scame.lighttubex.presentation.di.modules.AutocompleteModule;

import dagger.Component;

@PerActivity
@Component(modules = AutocompleteModule.class, dependencies = ApplicationComponent.class)
public interface AutocompleteComponent {

    void inject(AutocompleteActivity activity);
}
