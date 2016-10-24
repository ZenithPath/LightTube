package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.PostReplyUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter;
import com.example.scame.lighttube.presentation.presenters.ReplyInputPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


import static com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter.*;

@Module
public class InsertReplyModule {

    @Provides
    @PerActivity
    IReplyInputPresenter<ReplyView> provideReplyInputPresenter(@Named("replyInput")SubscriptionsHandler subscriptionsHandler,
                                                               PostReplyUseCase postReplyUseCase) {
        return new ReplyInputPresenterImp<>(postReplyUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    PostReplyUseCase providePostReplyUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                             ICommentsDataManager commentsDataManager) {
        return new PostReplyUseCase(subscribeOn, observeOn, commentsDataManager);
    }

    @Provides
    @Named("replyInput")
    @PerActivity
    SubscriptionsHandler provideReplyInputSubscriptionsHandler(PostReplyUseCase replyUseCase) {
        return new SubscriptionsHandler(replyUseCase);
    }
}
