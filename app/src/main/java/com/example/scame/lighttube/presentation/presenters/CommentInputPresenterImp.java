package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.PostThreadCommentUseCase;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

public class CommentInputPresenterImp<T extends ICommentInputPresenter.CommentInputView> implements ICommentInputPresenter<T> {

    private SubscriptionsHandler subscriptionsHandler;

    private PostThreadCommentUseCase postCommentUseCase;

    private T view;

    public CommentInputPresenterImp(PostThreadCommentUseCase postCommentUseCase,
                                    SubscriptionsHandler subscriptionsHandler) {
        this.subscriptionsHandler = subscriptionsHandler;
        this.postCommentUseCase = postCommentUseCase;
    }

    @Override
    public void postComment(String commentText, String videoId) {
        postCommentUseCase.setCommentText(commentText);
        postCommentUseCase.setVideoId(videoId);
        postCommentUseCase.execute(new PostResponseSubscriber());
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

    private final class PostResponseSubscriber extends DefaultSubscriber<ThreadCommentModel> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxPostResponseErr", e.getMessage());
        }

        @Override
        public void onNext(ThreadCommentModel threadCommentModel) {
            super.onNext(threadCommentModel);

            if (view != null) {
                view.displayPostedComment(threadCommentModel);
            }
        }
    }
}
