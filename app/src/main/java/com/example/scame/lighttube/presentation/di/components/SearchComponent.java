package com.example.scame.lighttube.presentation.di.components;


import com.example.scame.lighttube.presentation.activities.SearchActivity;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.di.modules.SearchModule;
import com.example.scame.lighttube.presentation.fragments.AutocompleteFragment;
import com.example.scame.lighttube.presentation.fragments.SearchResultsFragment;

import dagger.Component;

@PerActivity
@Component(modules = SearchModule.class, dependencies = ApplicationComponent.class)
public interface SearchComponent {

    void inject(SearchActivity activity);

    void inject(AutocompleteFragment fragment);

    void inject(SearchResultsFragment fragment);
}
