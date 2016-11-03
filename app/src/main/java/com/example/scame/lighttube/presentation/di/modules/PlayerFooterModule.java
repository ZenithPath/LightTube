package com.example.scame.lighttube.presentation.di.modules;


import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.data.repository.IStatisticsDataManager;
import com.example.scame.lighttube.data.repository.IUserChannelDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.DeleteCommentUseCase;
import com.example.scame.lighttube.domain.usecases.MarkAsSpamUseCase;
import com.example.scame.lighttube.domain.usecases.PostThreadCommentUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveCommentsUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveUserIdentifierUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateReplyUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateThreadUseCase;
import com.example.scame.lighttube.domain.usecases.VideoStatsUseCase;
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
                                                                DeleteCommentUseCase deleteCommentUseCase,
                                                                MarkAsSpamUseCase markAsSpamUseCase,
                                                                UpdateThreadUseCase updateThreadUseCase,
                                                                PostThreadCommentUseCase postThreadCommentUseCase,
                                                                UpdateReplyUseCase updateReplyUseCase,
                                                                @Named("footer")SubscriptionsHandler subscriptionsHandler) {
        return new PlayerFooterPresenterImp<>(retrieveCommentsUseCase,
                deleteCommentUseCase, markAsSpamUseCase, updateThreadUseCase, postThreadCommentUseCase,
                updateReplyUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    VideoStatsUseCase provideVideoStatsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                               IStatisticsDataManager statisticsDataManager) {
        return new VideoStatsUseCase(subscribeOn, observeOn, statisticsDataManager);
    }

    @Provides
    @PerActivity
    UpdateReplyUseCase provideUpdateReplyUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                 ICommentsDataManager commentsDataManager) {
        return new UpdateReplyUseCase(subscribeOn, observeOn, commentsDataManager);
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
                                                           ICommentsDataManager commentsDataManager,
                                                           IUserChannelDataManager userChannelDataManager,
                                                           IStatisticsDataManager statisticsDataManager) {

        return new RetrieveCommentsUseCase(subscribeOn, observeOn, commentsDataManager,
                statisticsDataManager, userChannelDataManager);
    }

    @Provides
    @Named("footer")
    @PerActivity
    SubscriptionsHandler provideCommentsSubscriptionsHandler(RetrieveCommentsUseCase retrieveCommentsUseCase,
                                                             DeleteCommentUseCase deleteCommentUseCase,
                                                             MarkAsSpamUseCase markAsSpamUseCase,
                                                             UpdateThreadUseCase updateThreadUseCase,
                                                             PostThreadCommentUseCase postThreadCommentUseCase) {
        return new SubscriptionsHandler(retrieveCommentsUseCase, deleteCommentUseCase,
                markAsSpamUseCase, updateThreadUseCase, postThreadCommentUseCase);
    }
}
