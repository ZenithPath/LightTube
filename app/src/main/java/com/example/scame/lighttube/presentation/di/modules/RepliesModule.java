package com.example.scame.lighttube.presentation.di.modules;


import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.PostReplyUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveRepliesUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.IRepliesPresenter;
import com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter;
import com.example.scame.lighttube.presentation.presenters.RepliesPresenterImp;
import com.example.scame.lighttube.presentation.presenters.ReplyInputPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.IRepliesPresenter.RepliesView;
import static com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter.*;

// contains classes that allow replies modifying

@Module
public class RepliesModule {

    @Provides
    @PerActivity
    IRepliesPresenter<RepliesView> provideRepliesPresenter(RetrieveRepliesUseCase retrieveRepliesUseCase,
                                                           @Named("replies")SubscriptionsHandler subscriptionsHandler) {
        return new RepliesPresenterImp<>(retrieveRepliesUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    IReplyInputPresenter<ReplyView> provideReplyInputPresenter(@Named("replyInput")SubscriptionsHandler subscriptionsHandler,
                                                                                    PostReplyUseCase postReplyUseCase) {
        return new ReplyInputPresenterImp<>(postReplyUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    RetrieveRepliesUseCase provideRetrieveRepliesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                         ICommentsDataManager commentsDataManager) {
        return new RetrieveRepliesUseCase(subscribeOn, observeOn, commentsDataManager);
    }

    @Provides
    @PerActivity
    PostReplyUseCase providePostReplyUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                             ICommentsDataManager commentsDataManager) {
        return new PostReplyUseCase(subscribeOn, observeOn, commentsDataManager);
    }

    @Provides
    @Named("replies")
    @PerActivity
    SubscriptionsHandler provideRepliesSubscriptionsHandler(RetrieveRepliesUseCase retrieveRepliesUseCase) {
        return new SubscriptionsHandler(retrieveRepliesUseCase);
    }

    @Provides
    @Named("replyInput")
    @PerActivity
    SubscriptionsHandler provideReplyInputSubscriptionsHandler(PostReplyUseCase replyUseCase) {
        return new SubscriptionsHandler(replyUseCase);
    }
}
