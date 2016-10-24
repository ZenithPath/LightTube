package com.example.scame.lighttube.presentation.di.modules;


import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.data.repository.IRatingDataManager;
import com.example.scame.lighttube.data.repository.IUserChannelDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.DeleteCommentUseCase;
import com.example.scame.lighttube.domain.usecases.MarkAsSpamUseCase;
import com.example.scame.lighttube.domain.usecases.PostReplyUseCase;
import com.example.scame.lighttube.domain.usecases.RateVideoUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveCommentsUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveRatingUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveUserIdentifierUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateThreadUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.IPlayerFooterPresenter;
import com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter;
import com.example.scame.lighttube.presentation.presenters.IVideoRatingPresenter;
import com.example.scame.lighttube.presentation.presenters.PlayerFooterPresenterImp;
import com.example.scame.lighttube.presentation.presenters.ReplyInputPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;
import com.example.scame.lighttube.presentation.presenters.VideoRatingPresenterImp;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.IPlayerFooterPresenter.FooterView;
import static com.example.scame.lighttube.presentation.presenters.IVideoRatingPresenter.*;
import static com.example.scame.lighttube.presentation.presenters.IReplyInputPresenter.*;

// contains classes that let to retrieve a list of comments and get videos rating (as well as rate)
// TODO: decompose
@Module
public class PlayerFooterModule {

    @Provides
    @PerActivity
    IPlayerFooterPresenter<FooterView> provideCommentsPresenter(RetrieveCommentsUseCase retrieveCommentsUseCase,
                                                                RetrieveUserIdentifierUseCase identifierUseCase,
                                                                DeleteCommentUseCase deleteCommentUseCase,
                                                                MarkAsSpamUseCase markAsSpamUseCase,
                                                                UpdateThreadUseCase updateThreadUseCase,
                                                                @Named("footer")SubscriptionsHandler subscriptionsHandler) {
        return new PlayerFooterPresenterImp<>(retrieveCommentsUseCase, identifierUseCase,
                deleteCommentUseCase, markAsSpamUseCase, updateThreadUseCase,subscriptionsHandler);
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
                                                             UpdateThreadUseCase updateThreadUseCase) {
        return new SubscriptionsHandler(retrieveCommentsUseCase, identifierUseCase,
                deleteCommentUseCase, markAsSpamUseCase, updateThreadUseCase);
    }

    @Provides
    @PerActivity
    IVideoRatingPresenter<PlayerView> provideRatingPresenter(RetrieveRatingUseCase retrieveRatingUseCase,
                                                             RateVideoUseCase rateVideoUseCase,
                                                             @Named("rating")SubscriptionsHandler subscriptionsHandler) {

        return new VideoRatingPresenterImp<>(retrieveRatingUseCase, rateVideoUseCase, subscriptionsHandler);
    }

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
    @Named("rating")
    @PerActivity
    SubscriptionsHandler provideRatingSubscriptionsHandler(RetrieveRatingUseCase retrieveRatingUseCase,
                                                           RateVideoUseCase rateVideoUseCase) {

        return new SubscriptionsHandler(retrieveRatingUseCase, rateVideoUseCase);
    }

    // new dependencies

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
