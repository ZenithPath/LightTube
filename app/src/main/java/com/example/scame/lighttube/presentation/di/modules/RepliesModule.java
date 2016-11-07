package com.example.scame.lighttube.presentation.di.modules;


import com.example.scame.lighttube.data.repository.CommentsRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.DeleteCommentUseCase;
import com.example.scame.lighttube.domain.usecases.GetRepliesUseCase;
import com.example.scame.lighttube.domain.usecases.MarkAsSpamUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateCommentUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateReplyUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.RepliesPresenter;
import com.example.scame.lighttube.presentation.presenters.RepliesPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.RepliesPresenter.RepliesView;

// contains classes that allow replies (and primary comment) modifying

@Module(includes = ReplyInputModule.class)
public class RepliesModule {

    @Provides
    @PerActivity
    RepliesPresenter<RepliesView> provideRepliesPresenter(GetRepliesUseCase getRepliesUseCase,
                                                          DeleteCommentUseCase deleteCommentUseCase,
                                                          MarkAsSpamUseCase markAsSpamUseCase,
                                                          UpdateReplyUseCase updateReplyUseCase,
                                                          UpdateCommentUseCase updatePrimaryUseCase,
                                                          @Named("replies")SubscriptionsHandler subscriptionsHandler) {
        return new RepliesPresenterImp<>(getRepliesUseCase, deleteCommentUseCase,
                markAsSpamUseCase, updateReplyUseCase, updatePrimaryUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    UpdateCommentUseCase updatePrimaryUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                              CommentsRepository dataManager) {
        return new UpdateCommentUseCase(subscribeOn, observeOn, dataManager);
    }

    @Provides
    @PerActivity
    UpdateReplyUseCase provideUpdateReplyUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                 CommentsRepository dataManager) {
        return new UpdateReplyUseCase(subscribeOn, observeOn, dataManager);
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
    GetRepliesUseCase provideRetrieveRepliesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                    CommentsRepository commentsRepository) {
        return new GetRepliesUseCase(subscribeOn, observeOn, commentsRepository);
    }

    @Provides
    @Named("replies")
    @PerActivity
    SubscriptionsHandler provideRepliesSubscriptionsHandler(GetRepliesUseCase getRepliesUseCase,
                                                            DeleteCommentUseCase deleteCommentUseCase,
                                                            MarkAsSpamUseCase markAsSpamUseCase,
                                                            UpdateReplyUseCase updateReplyUseCase,
                                                            UpdateCommentUseCase updatePrimaryUseCase) {
        return new SubscriptionsHandler(getRepliesUseCase, deleteCommentUseCase, markAsSpamUseCase,
                updateReplyUseCase, updatePrimaryUseCase);
    }
}
