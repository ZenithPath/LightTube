package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.DeleteCommentUseCase;
import com.example.scame.lighttube.domain.usecases.MarkAsSpamUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveRepliesUseCase;
import com.example.scame.lighttube.presentation.model.ReplyListModel;

public class RepliesPresenterImp<T extends IRepliesPresenter.RepliesView> implements IRepliesPresenter<T> {

    private RetrieveRepliesUseCase retrieveRepliesUseCase;

    private DeleteCommentUseCase deleteCommentUseCase;

    private MarkAsSpamUseCase markAsSpamUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private int deletionIndex;

    private int spamIndex;

    private T view;

    public RepliesPresenterImp(RetrieveRepliesUseCase retrieveRepliesUseCase,
                               DeleteCommentUseCase deleteCommentUseCase,
                               MarkAsSpamUseCase markAsSpamUseCase,
                               SubscriptionsHandler subscriptionsHandler) {
        this.markAsSpamUseCase = markAsSpamUseCase;
        this.retrieveRepliesUseCase = retrieveRepliesUseCase;
        this.deleteCommentUseCase = deleteCommentUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void getRepliesList(String parentId) {
        retrieveRepliesUseCase.setParentId(parentId);
        retrieveRepliesUseCase.execute(new RepliesSubscriber());
    }

    @Override
    public void deleteReply(String replyId, int position) {
        deletionIndex = position;
        deleteCommentUseCase.setCommentId(replyId);
        deleteCommentUseCase.execute(new DeletionSubscriber());
    }

    @Override
    public void markAsSpam(String replyId, int position) {
        spamIndex = position;
        markAsSpamUseCase.setCommentId(replyId);
        markAsSpamUseCase.execute(new SpamSubscriber());
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        view = null;
        subscriptionsHandler.unsubscribe();
    }

    private final class RepliesSubscriber extends DefaultSubscriber<ReplyListModel> {

        @Override
        public void onNext(ReplyListModel replyListModel) {
            super.onNext(replyListModel);

            if (view != null) {
                view.displayReplies(replyListModel);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxRepliesError", e.getLocalizedMessage());
        }
    }

    private final class DeletionSubscriber extends DefaultSubscriber<Void> {

        @Override
        public void onCompleted() {
            super.onCompleted();

            if (view != null) {
                view.onDeletedReply(deletionIndex);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxDeletionErr", e.getLocalizedMessage());
        }
    }

    private final class SpamSubscriber extends DefaultSubscriber<Void> {

        @Override
        public void onCompleted() {
            super.onCompleted();

            if (view != null) {
                view.onMarkedAsSpam(spamIndex);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxSpamErr", e.getLocalizedMessage());
        }
    }
}
