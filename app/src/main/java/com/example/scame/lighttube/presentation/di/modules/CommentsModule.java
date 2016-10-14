package com.example.scame.lighttube.presentation.di.modules;


import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.PostThreadCommentUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.CommentInputPresenterImp;
import com.example.scame.lighttube.presentation.presenters.ICommentInputPresenter;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.ICommentInputPresenter.CommentInputView;

// contains classes that allow thread comments modifying

@Module
public class CommentsModule {

    @Provides
    @PerActivity
    ICommentInputPresenter<CommentInputView> provideCommentInputPresenter(SubscriptionsHandler subscriptionsHandler,
                                                                          PostThreadCommentUseCase threadCommentUseCase) {
        return new CommentInputPresenterImp<>(threadCommentUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    PostThreadCommentUseCase providePostCommentUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                       ICommentsDataManager commentsDataManager) {
        return new PostThreadCommentUseCase(subscribeOn, observeOn, commentsDataManager);
    }

    @Provides
    @PerActivity
    SubscriptionsHandler provideInputSubscriptionsHandler(PostThreadCommentUseCase threadCommentUseCase) {
        return new SubscriptionsHandler(threadCommentUseCase);
    }
}
