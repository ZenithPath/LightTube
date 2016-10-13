package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.data.repository.IRatingDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.PostReplyUseCase;
import com.example.scame.lighttube.domain.usecases.PostThreadCommentUseCase;
import com.example.scame.lighttube.domain.usecases.RateVideoUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveCommentsUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveRatingUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveRepliesUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.CommentInputPresenterImp;
import com.example.scame.lighttube.presentation.presenters.ICommentInputPresenter;
import com.example.scame.lighttube.presentation.presenters.IRepliesPresenter;
import com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter;
import com.example.scame.lighttube.presentation.presenters.PlayerFooterPresenterImp;
import com.example.scame.lighttube.presentation.presenters.IPlayerFooterPresenter;
import com.example.scame.lighttube.presentation.presenters.IPlayerHeaderPresenter;
import com.example.scame.lighttube.presentation.presenters.PlayerHeaderPresenterImp;
import com.example.scame.lighttube.presentation.presenters.RepliesPresenterImp;
import com.example.scame.lighttube.presentation.presenters.ReplyInputPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.IPlayerFooterPresenter.*;
import static com.example.scame.lighttube.presentation.presenters.IPlayerHeaderPresenter.*;

import static com.example.scame.lighttube.presentation.presenters.IRepliesPresenter.*;
import static com.example.scame.lighttube.presentation.presenters.ICommentInputPresenter.*;
import static com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter.*;

// TODO: decompose

@Module
public class PlayerModule {

    @Provides
    @PerActivity
    RetrieveRatingUseCase provideRetrieveRatingUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                       IRatingDataManager ratingsDataManager) {

        return new RetrieveRatingUseCase(subscribeOn, observeOn, ratingsDataManager);
    }

    @Provides
    @PerActivity
    RateVideoUseCase provideRateVideoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                             IRatingDataManager ratingsDataManager) {

        return new RateVideoUseCase(subscribeOn, observeOn, ratingsDataManager);
    }

    @Provides
    @PerActivity
    RetrieveCommentsUseCase provideRetrieveCommentsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                           ICommentsDataManager commentsDataManager) {

        return new RetrieveCommentsUseCase(subscribeOn, observeOn, commentsDataManager);
    }

    @Provides
    @PerActivity
    RetrieveRepliesUseCase provideRetrieveRepliesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                         ICommentsDataManager commentsDataManager) {
        return new RetrieveRepliesUseCase(subscribeOn, observeOn, commentsDataManager);
    }

    @Provides
    @PerActivity
    PostThreadCommentUseCase providePostCommentUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                       ICommentsDataManager commentsDataManager) {
        return new PostThreadCommentUseCase(subscribeOn, observeOn, commentsDataManager);
    }

    @Provides
    @PerActivity
    PostReplyUseCase providePostReplyUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                             ICommentsDataManager commentsDataManager) {
        return new PostReplyUseCase(subscribeOn, observeOn, commentsDataManager);
    }

    @Provides
    @PerActivity
    IReplyInputPresenter<ReplyView> provideReplyInputPresenter(@Named("replyInput")SubscriptionsHandler subscriptionsHandler,
                                                               PostReplyUseCase postReplyUseCase) {
        return new ReplyInputPresenterImp<>(postReplyUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    ICommentInputPresenter<CommentInputView> provideCommentInputPresenter(
            @Named("threadInput")SubscriptionsHandler subscriptionsHandler, PostThreadCommentUseCase threadCommentUseCase) {
        return new CommentInputPresenterImp<>(threadCommentUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    IRepliesPresenter<RepliesView> provideRepliesPresenter(RetrieveRepliesUseCase retrieveRepliesUseCase,
                                                           @Named("replies")SubscriptionsHandler subscriptionsHandler) {
        return new RepliesPresenterImp<>(retrieveRepliesUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    IPlayerHeaderPresenter<PlayerView> providePlayerPresenter(RetrieveRatingUseCase retrieveRatingUseCase,
                                                              RateVideoUseCase rateVideoUseCase,
                                                              @Named("header")SubscriptionsHandler subscriptionsHandler) {

        return new PlayerHeaderPresenterImp<>(retrieveRatingUseCase, rateVideoUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    IPlayerFooterPresenter<CommentsView> provideCommentsPresenter(RetrieveCommentsUseCase retrieveCommentsUseCase,
                                                                  @Named("comments")SubscriptionsHandler subscriptionsHandler) {

        return new PlayerFooterPresenterImp<>(retrieveCommentsUseCase, subscriptionsHandler);
    }

    @Provides
    @Named("threadInput")
    @PerActivity
    SubscriptionsHandler provideInputSubscriptionsHandler(PostThreadCommentUseCase threadCommentUseCase) {
        return new SubscriptionsHandler(threadCommentUseCase);
    }

    @Provides
    @Named("header")
    @PerActivity
    SubscriptionsHandler provideHeaderSubscriptionsHandler(RetrieveRatingUseCase retrieveRatingUseCase,
                                                     RateVideoUseCase rateVideoUseCase) {

        return new SubscriptionsHandler(retrieveRatingUseCase, rateVideoUseCase);
    }

    @Provides
    @Named("comments")
    @PerActivity
    SubscriptionsHandler provideCommentsSubscriptionsHandler(RetrieveCommentsUseCase retrieveCommentsUseCase) {
        return new SubscriptionsHandler(retrieveCommentsUseCase);
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
