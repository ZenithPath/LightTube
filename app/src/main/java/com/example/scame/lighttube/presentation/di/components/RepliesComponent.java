package com.example.scame.lighttube.presentation.di.components;

import com.example.scame.lighttube.presentation.adapters.player.CommentsAdapter;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.di.modules.RepliesModule;
import com.example.scame.lighttube.presentation.fragments.RepliesFragment;

import dagger.Component;

@PerActivity
@Component(modules = RepliesModule.class, dependencies = ApplicationComponent.class)
public interface RepliesComponent {

    void inject(RepliesFragment fragment);

    void inject(CommentsAdapter commentsAdapter);
}
