package com.example.scame.lighttube.presentation.di.modules;


import com.example.scame.lighttube.data.repository.CommentsRepository;
import com.example.scame.lighttube.data.repository.StatisticsRepository;
import com.example.scame.lighttube.data.repository.UserChannelRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.DeleteCommentUseCase;
import com.example.scame.lighttube.domain.usecases.FooterInitializationUseCase;
import com.example.scame.lighttube.domain.usecases.GetCommentsUseCase;
import com.example.scame.lighttube.domain.usecases.GetUserIdentifierUseCase;
import com.example.scame.lighttube.domain.usecases.GetVideoStatsUseCase;
import com.example.scame.lighttube.domain.usecases.MarkAsSpamUseCase;
import com.example.scame.lighttube.domain.usecases.PostCommentUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateCommentUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateReplyUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.PlayerFooterPresenter;
import com.example.scame.lighttube.presentation.presenters.PlayerFooterPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.PlayerFooterPresenter.FooterView;


@Module(includes = {ReplyInputModule.class, RatingModule.class})
public class PlayerFooterModule {

    @Provides
    @PerActivity
    PlayerFooterPresenter<FooterView> provideCommentsPresenter(FooterInitializationUseCase footerInitializationUseCase,
                                                               GetCommentsUseCase getCommentsUseCase,
                                                               DeleteCommentUseCase deleteCommentUseCase,
                                                               MarkAsSpamUseCase markAsSpamUseCase,
                                                               UpdateCommentUseCase updateCommentUseCase,
                                                               PostCommentUseCase postCommentUseCase,
                                                               UpdateReplyUseCase updateReplyUseCase,
                                                               @Named("footer")SubscriptionsHandler subscriptionsHandler) {
        return new PlayerFooterPresenterImp<>(footerInitializationUseCase,
                deleteCommentUseCase, markAsSpamUseCase, updateCommentUseCase, postCommentUseCase,
                updateReplyUseCase, getCommentsUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    GetCommentsUseCase provideCommentsRetreivingUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                        CommentsRepository dataManager) {
        return new GetCommentsUseCase(subscribeOn, observeOn, dataManager);
    }

    @Provides
    @PerActivity
    GetVideoStatsUseCase provideVideoStatsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                  StatisticsRepository statisticsDataManager) {
        return new GetVideoStatsUseCase(subscribeOn, observeOn, statisticsDataManager);
    }

    @Provides
    @PerActivity
    UpdateReplyUseCase provideUpdateReplyUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                 CommentsRepository commentsRepository) {
        return new UpdateReplyUseCase(subscribeOn, observeOn, commentsRepository);
    }

    @Provides
    @PerActivity
    PostCommentUseCase providePostCommentUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                 CommentsRepository commentsRepository) {
        return new PostCommentUseCase(subscribeOn, observeOn, commentsRepository);
    }

    @Provides
    @PerActivity
    UpdateCommentUseCase provideUpdateThreadUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                    CommentsRepository dataManager) {
        return new UpdateCommentUseCase(subscribeOn, observeOn, dataManager);
    }

    @Provides
    @PerActivity
    MarkAsSpamUseCase provideMarkAsSpamUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                               CommentsRepository dataManager) {
        return new MarkAsSpamUseCase(subscribeOn, observeOn, dataManager);
    }

    @Provides
    @PerActivity
    DeleteCommentUseCase provideDeleteCommentUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                     CommentsRepository dataManager) {
        return new DeleteCommentUseCase(subscribeOn, observeOn, dataManager);
    }

    @Provides
    @PerActivity
    GetUserIdentifierUseCase provideUserIdentifierUseCase(ObserveOn observeOn, SubscribeOn subscribeOn,
                                                          UserChannelRepository dataManager) {
        return new GetUserIdentifierUseCase(subscribeOn, observeOn, dataManager);
    }

    @Provides
    @PerActivity
    FooterInitializationUseCase provideRetrieveCommentsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                               CommentsRepository commentsRepository,
                                                               UserChannelRepository userChannelDataManager,
                                                               StatisticsRepository statisticsDataManager) {

        return new FooterInitializationUseCase(subscribeOn, observeOn, commentsRepository,
                statisticsDataManager, userChannelDataManager);
    }

    @Provides
    @Named("footer")
    @PerActivity
    SubscriptionsHandler provideCommentsSubscriptionsHandler(FooterInitializationUseCase footerInitializationUseCase,
                                                             DeleteCommentUseCase deleteCommentUseCase,
                                                             MarkAsSpamUseCase markAsSpamUseCase,
                                                             UpdateCommentUseCase updateCommentUseCase,
                                                             PostCommentUseCase postCommentUseCase) {
        return new SubscriptionsHandler(footerInitializationUseCase, deleteCommentUseCase,
                markAsSpamUseCase, updateCommentUseCase, postCommentUseCase);
    }
}
