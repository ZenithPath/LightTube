package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.PostReplyUseCase;
import com.example.scame.lighttube.presentation.model.ReplyModel;

public class ReplyInputPresenterImp<T extends ReplyInputPresenter.ReplyView> implements ReplyInputPresenter<T> {

    private SubscriptionsHandler subscriptionsHandler;

    private PostReplyUseCase postReplyUseCase;

    private T view;

    public ReplyInputPresenterImp(PostReplyUseCase postReplyUseCase, SubscriptionsHandler subscriptionsHandler) {
        this.subscriptionsHandler = subscriptionsHandler;
        this.postReplyUseCase = postReplyUseCase;
    }

    @Override
    public void postReply(String parentId, String replyText) {
        postReplyUseCase.setParentId(parentId);
        postReplyUseCase.setReplyText(replyText);
        postReplyUseCase.execute(new ReplyInputSubscriber());
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

    private final class ReplyInputSubscriber extends DefaultSubscriber<ReplyModel> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxReplyInputErr", e.getLocalizedMessage());
        }

        @Override
        public void onNext(ReplyModel replyModel) {
            super.onNext(replyModel);

            if (view != null) {
                view.displayReply(replyModel);
            }
        }
    }
}
