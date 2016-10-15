package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;
import android.util.Pair;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.DeleteCommentUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveCommentsUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveUserIdentifierUseCase;
import com.example.scame.lighttube.presentation.model.CommentListModel;

import static android.util.Log.i;

public class PlayerFooterPresenterImp<T extends IPlayerFooterPresenter.FooterView> implements IPlayerFooterPresenter<T> {

    private RetrieveCommentsUseCase retrieveCommentsUseCase;

    private RetrieveUserIdentifierUseCase identifierUseCase;

    private DeleteCommentUseCase deleteCommentUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private T view;

    private CommentListModel commentListModel;

    private Pair<Integer, Integer> commentIndex;

    public PlayerFooterPresenterImp(RetrieveCommentsUseCase retrieveCommentsUseCase,
                                    RetrieveUserIdentifierUseCase identifierUseCase,
                                    DeleteCommentUseCase deleteCommentUseCase,
                                    SubscriptionsHandler subscriptionsHandler) {
        this.identifierUseCase = identifierUseCase;
        this.retrieveCommentsUseCase = retrieveCommentsUseCase;
        this.deleteCommentUseCase = deleteCommentUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void getCommentList(String videoId) {
        retrieveCommentsUseCase.setVideoId(videoId);
        retrieveCommentsUseCase.execute(new RetrieveCommentsSubscriber());
    }

    @Override
    public void deleteThreadComment(String commentId, Pair<Integer, Integer> commentIndex) {
        this.commentIndex = commentIndex;
        deleteCommentUseCase.setCommentId(commentId);
        deleteCommentUseCase.execute(new DeletionSubscriber());
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

    private final class RetrieveCommentsSubscriber extends DefaultSubscriber<CommentListModel> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            i("onxCommentsError", e.getLocalizedMessage());
        }

        @Override
        public void onNext(CommentListModel commentListModel) {
            super.onNext(commentListModel);
            PlayerFooterPresenterImp.this.commentListModel = commentListModel;
        }

        @Override
        public void onCompleted() {
            super.onCompleted();
            identifierUseCase.execute(new IdentifierSubscriber());
        }
    }

    private final class IdentifierSubscriber extends DefaultSubscriber<String> {

        @Override
        public void onNext(String identifier) {
            super.onNext(identifier);

            view.displayComments(commentListModel, identifier);
        }


        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxErr", e.getLocalizedMessage());
        }
    }

    private final class DeletionSubscriber extends DefaultSubscriber<Void> {

        @Override
        public void onCompleted() {
            super.onCompleted();

            if (view != null) {
                view.onCommentDeleted(commentIndex);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxDeletionErr", e.getLocalizedMessage());
        }
    }
}
