package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.PostThreadCommentUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveCommentsUseCase;
import com.example.scame.lighttube.presentation.model.CommentListModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import static android.util.Log.i;

public class PlayerFooterPresenterImp<T extends IPlayerFooterPresenter.CommentsView> implements IPlayerFooterPresenter<T> {

    private PostThreadCommentUseCase postCommentUseCase;

    private RetrieveCommentsUseCase retrieveCommentsUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private T view;

    public PlayerFooterPresenterImp(RetrieveCommentsUseCase retrieveCommentsUseCase,
                                    PostThreadCommentUseCase postCommentUseCase,
                                    SubscriptionsHandler subscriptionsHandler) {

        this.postCommentUseCase = postCommentUseCase;
        this.retrieveCommentsUseCase = retrieveCommentsUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void getCommentList(String videoId) {
        retrieveCommentsUseCase.setVideoId(videoId);
        retrieveCommentsUseCase.execute(new RetrieveCommentsSubscriber());
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
            i("onxPostResponseErr", e.getMessage());
        }

        @Override
        public void onNext(ThreadCommentModel threadCommentModel) {
            super.onNext(threadCommentModel);

            if (view != null) {
                view.displayPostedComment(threadCommentModel);
            }
        }
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

            if (view != null) {
                view.displayComments(commentListModel);
            }
        }
    }
}
