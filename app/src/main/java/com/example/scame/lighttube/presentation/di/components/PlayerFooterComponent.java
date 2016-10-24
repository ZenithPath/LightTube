package com.example.scame.lighttube.presentation.di.components;

import com.example.scame.lighttube.presentation.adapters.player.threads.HeaderHolder;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.di.modules.PlayerFooterModule;
import com.example.scame.lighttube.presentation.fragments.PlayerFooterFragment;

import dagger.Component;

@PerActivity
@Component(modules = PlayerFooterModule.class, dependencies = ApplicationComponent.class)
public interface PlayerFooterComponent {

    void inject(PlayerFooterFragment fragment);

    void inject(HeaderHolder headerViewHolder);
}
