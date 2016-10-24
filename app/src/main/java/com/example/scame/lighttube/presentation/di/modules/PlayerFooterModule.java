package com.example.scame.lighttube.presentation.di.modules;


import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.data.repository.IUserChannelDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.DeleteCommentUseCase;
import com.example.scame.lighttube.domain.usecases.MarkAsSpamUseCase;
import com.example.scame.lighttube.domain.usecases.PostThreadCommentUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveCommentsUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveUserIdentifierUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateThreadUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.IPlayerFooterPresenter;
import com.example.scame.lighttube.presentation.presenters.PlayerFooterPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.IPlayerFooterPresenter.FooterView;


@Module(includes = {InsertReplyModule.class, RatingModule.class})
public class PlayerFooterModule {

    @Provides
    @PerActivity
    IPlayerFooterPresenter<FooterView> provideCommentsPresenter(RetrieveCommentsUseCase retrieveCommentsUseCase,
                                                                RetrieveUserIdentifierUseCase identifierUseCase,
                                                                DeleteCommentUseCase deleteCommentUseCase,
                                                                MarkAsSpamUseCase markAsSpamUseCase,
                                                                UpdateThreadUseCase updateThreadUseCase,
                                                                PostThreadCommentUseCase postThreadCommentUseCase,
                                                                @Named("footer")SubscriptionsHandler subscriptionsHandler) {
        return new PlayerFooterPresenterImp<>(retrieveCommentsUseCase, identifierUseCase,
                deleteCommentUseCase, markAsSpamUseCase, updateThreadUseCase, postThreadCommentUseCase,
                subscriptionsHandler);
    }

    @Provides
    @PerActivity
    PostThreadCommentUseCase providePostCommentUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                       ICommentsDataManager commentsDataManager) {
        return new PostThreadCommentUseCase(subscribeOn, observeOn, commentsDataManager);
    }

    @Provides
    @PerActivity
    UpdateThreadUseCase provideUpdateThreadUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                   ICommentsDataManager dataManager) {
        return new UpdateThreadUseCase(subscribeOn, observeOn, dataManager);
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
    RetrieveUserIdentifierUseCase provideUserIdentifierUseCase(ObserveOn observeOn, SubscribeOn subscribeOn,
                                                               IUserChannelDataManager dataManager) {
        return new RetrieveUserIdentifierUseCase(subscribeOn, observeOn, dataManager);
    }

    @Provides
    @PerActivity
    RetrieveCommentsUseCase provideRetrieveCommentsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                           ICommentsDataManager commentsDataManager) {

        return new RetrieveCommentsUseCase(subscribeOn, observeOn, commentsDataManager);
    }

    @Provides
    @Named("footer")
    @PerActivity
    SubscriptionsHandler provideCommentsSubscriptionsHandler(RetrieveCommentsUseCase retrieveCommentsUseCase,
                                                             RetrieveUserIdentifierUseCase identifierUseCase,
                                                             DeleteCommentUseCase deleteCommentUseCase,
                                                             MarkAsSpamUseCase markAsSpamUseCase,
                                                             UpdateThreadUseCase updateThreadUseCase,
                                                             PostThreadCommentUseCase postThreadCommentUseCase) {
        return new SubscriptionsHandler(retrieveCommentsUseCase, identifierUseCase,
                deleteCommentUseCase, markAsSpamUseCase, updateThreadUseCase, postThreadCommentUseCase);
    }
}
