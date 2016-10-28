package com.example.scame.lighttube.presentation.di.modules;


import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.DeleteCommentUseCase;
import com.example.scame.lighttube.domain.usecases.MarkAsSpamUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveRepliesUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateReplyUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateThreadUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.IRepliesPresenter;
import com.example.scame.lighttube.presentation.presenters.RepliesPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.IRepliesPresenter.RepliesView;

// contains classes that allow replies (and primary comment) modifying

@Module(includes = InsertReplyModule.class)
public class RepliesModule {

    @Provides
    @PerActivity
    IRepliesPresenter<RepliesView> provideRepliesPresenter(RetrieveRepliesUseCase retrieveRepliesUseCase,
                                                           DeleteCommentUseCase deleteCommentUseCase,
                                                           MarkAsSpamUseCase markAsSpamUseCase,
                                                           UpdateReplyUseCase updateReplyUseCase,
                                                           UpdateThreadUseCase updatePrimaryUseCase,
                                                           @Named("replies")SubscriptionsHandler subscriptionsHandler) {
        return new RepliesPresenterImp<>(retrieveRepliesUseCase, deleteCommentUseCase,
                markAsSpamUseCase, updateReplyUseCase, updatePrimaryUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    UpdateThreadUseCase updatePrimaryUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                             ICommentsDataManager dataManager) {
        return new UpdateThreadUseCase(subscribeOn, observeOn, dataManager);
    }

    @Provides
    @PerActivity
    UpdateReplyUseCase provideUpdateReplyUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                 ICommentsDataManager dataManager) {
        return new UpdateReplyUseCase(subscribeOn, observeOn, dataManager);
    }

    @Provides
    @PerActivity
    MarkAsSpamUseCase provideMarkAsSpamUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                               ICommentsDataManager dataManager) {
        return new MarkAsSpamUseCase(subscribeOn, observeOn, dataManager);
    }

    @Provides
    @PerActivity
    DeleteCommentUseCase provideDeleteCommentUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                     ICommentsDataManager dataManager) {
        return new DeleteCommentUseCase(subscribeOn, observeOn, dataManager);
    }

    @Provides
    @PerActivity
    RetrieveRepliesUseCase provideRetrieveRepliesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                         ICommentsDataManager commentsDataManager) {
        return new RetrieveRepliesUseCase(subscribeOn, observeOn, commentsDataManager);
    }

    @Provides
    @Named("replies")
    @PerActivity
    SubscriptionsHandler provideRepliesSubscriptionsHandler(RetrieveRepliesUseCase retrieveRepliesUseCase,
                                                            DeleteCommentUseCase deleteCommentUseCase,
                                                            MarkAsSpamUseCase markAsSpamUseCase,
                                                            UpdateReplyUseCase updateReplyUseCase,
                                                            UpdateThreadUseCase updatePrimaryUseCase) {
        return new SubscriptionsHandler(retrieveRepliesUseCase, deleteCommentUseCase, markAsSpamUseCase,
                updateReplyUseCase, updatePrimaryUseCase);
    }
}
