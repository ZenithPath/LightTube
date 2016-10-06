package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.RetrieveCommentsUseCase;
import com.example.scame.lighttube.presentation.model.CommentListModel;

public class CommentsPresenterImp<T extends ICommentsPresenter.CommentsView> implements ICommentsPresenter<T> {

    private RetrieveCommentsUseCase retrieveCommentsUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private T view;

    public CommentsPresenterImp(RetrieveCommentsUseCase retrieveCommentsUseCase,
                                SubscriptionsHandler subscriptionsHandler) {

        this.retrieveCommentsUseCase = retrieveCommentsUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void getCommentList(String videoId) {
        retrieveCommentsUseCase.setVideoId(videoId);
        retrieveCommentsUseCase.execute(new RetrieveCommentsSubscriber());
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
        subscriptionsHandler.unsubscribe();
    }

    private final class RetrieveCommentsSubscriber extends DefaultSubscriber<CommentListModel> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxCommentsError", e.getLocalizedMessage());
        }

        @Override
        public void onNext(CommentListModel commentListModel) {
            super.onNext(commentListModel);

            view.displayComments(commentListModel);
        }
    }
}
