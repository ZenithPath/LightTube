package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.RetrieveRepliesUseCase;
import com.example.scame.lighttube.presentation.model.ReplyListModel;

public class RepliesPresenterImp<T extends IRepliesPresenter.RepliesView> implements IRepliesPresenter<T> {

    private RetrieveRepliesUseCase retrieveRepliesUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private T view;

    public RepliesPresenterImp(RetrieveRepliesUseCase retrieveRepliesUseCase,
                               SubscriptionsHandler subscriptionsHandler) {
        this.retrieveRepliesUseCase = retrieveRepliesUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void getRepliesList(String parentId) {
        retrieveRepliesUseCase.setParentId(parentId);
        retrieveRepliesUseCase.execute(new RepliesSubscriber());
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
                Log.i("onxRepliesFetched", "" + replyListModel.getReplyModels().size());
                view.displayReplies(replyListModel);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxRepliesError", e.getLocalizedMessage());
        }
    }
}
