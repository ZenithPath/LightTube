package com.example.scame.lighttubex.presentation.di.components;


import com.example.scame.lighttubex.presentation.activities.SearchActivity;
import com.example.scame.lighttubex.presentation.di.PerActivity;
import com.example.scame.lighttubex.presentation.di.modules.SearchModule;
import com.example.scame.lighttubex.presentation.fragments.AutocompleteFragment;

import dagger.Component;

@PerActivity
@Component(modules = SearchModule.class, dependencies = ApplicationComponent.class)
public interface SearchComponent {

    void inject(SearchActivity activity);

    void inject(AutocompleteFragment fragment);
}
