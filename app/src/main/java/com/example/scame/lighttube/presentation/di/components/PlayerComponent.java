package com.example.scame.lighttube.presentation.di.components;


import com.example.scame.lighttube.presentation.adapters.player.HeaderViewHolder;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.di.modules.PlayerModule;
import com.example.scame.lighttube.presentation.fragments.PlayerFooterFragment;
import com.example.scame.lighttube.presentation.fragments.RepliesFragment;

import dagger.Component;

@PerActivity
@Component(modules = PlayerModule.class, dependencies = ApplicationComponent.class)
public interface PlayerComponent {

    void inject(PlayerFooterFragment fragment);

    void inject(HeaderViewHolder headerViewHolder);

    void inject(RepliesFragment fragment);
}
