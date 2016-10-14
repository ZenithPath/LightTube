package com.example.scame.lighttube.presentation.di.components;


import com.example.scame.lighttube.presentation.adapters.player.CommentInputViewHolder;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.di.modules.CommentsModule;

import dagger.Component;

@PerActivity
@Component(modules = CommentsModule.class, dependencies = ApplicationComponent.class)
public interface CommentsComponent {

    void inject(CommentInputViewHolder commentInputViewHolder);
}
