package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.DeleteCommentUseCase;
import com.example.scame.lighttube.domain.usecases.MarkAsSpamUseCase;
import com.example.scame.lighttube.domain.usecases.PostThreadCommentUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveCommentsUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateReplyUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateThreadUseCase;
import com.example.scame.lighttube.presentation.model.MergedCommentsModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.List;

import static android.util.Log.i;

public class PlayerFooterPresenterImp<T extends IPlayerFooterPresenter.FooterView> implements IPlayerFooterPresenter<T> {

    private RetrieveCommentsUseCase retrieveCommentsUseCase;

    private DeleteCommentUseCase deleteCommentUseCase;

    private MarkAsSpamUseCase markAsSpamUseCase;

    private UpdateThreadUseCase updateThreadUseCase;

    private PostThreadCommentUseCase postCommentUseCase;

    private UpdateReplyUseCase updateReplyUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private T view;

    // refactor
    private List<ThreadCommentModel> models;

    public PlayerFooterPresenterImp(RetrieveCommentsUseCase retrieveCommentsUseCase,
                                    DeleteCommentUseCase deleteCommentUseCase,
                                    MarkAsSpamUseCase markAsSpamUseCase,
                                    UpdateThreadUseCase updateThreadUseCase,
                                    PostThreadCommentUseCase postCommentUseCase,
                                    UpdateReplyUseCase updateReplyUseCase,
                                    SubscriptionsHandler subscriptionsHandler) {
        this.updateReplyUseCase = updateReplyUseCase;
        this.postCommentUseCase = postCommentUseCase;
        this.markAsSpamUseCase = markAsSpamUseCase;
        this.retrieveCommentsUseCase = retrieveCommentsUseCase;
        this.deleteCommentUseCase = deleteCommentUseCase;
        this.updateThreadUseCase = updateThreadUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void getCommentList(String videoId) {
        retrieveCommentsUseCase.setVideoId(videoId);
        retrieveCommentsUseCase.execute(new RetrieveCommentsSubscriber());
    }

    @Override
    public void deleteThreadComment(String commentId) {
        deleteCommentUseCase.setCommentId(commentId);
        deleteCommentUseCase.execute(new DeletionSubscriber());
    }

    @Override
    public void markAsSpam(String commentId) {
        markAsSpamUseCase.setCommentId(commentId);
        markAsSpamUseCase.execute(new SpamSubscriber());
    }

    @Override
    public void updateComment(String commentId, String updatedText) {
        updateThreadUseCase.setCommentId(commentId);
        updateThreadUseCase.setUpdatedText(updatedText);
        updateThreadUseCase.execute(new UpdateThreadSubscriber());
    }

    @Override
    public void postComment(String commentText, String videoId) {
        postCommentUseCase.setCommentText(commentText);
        postCommentUseCase.setVideoId(videoId);
        postCommentUseCase.execute(new PostResponseSubscriber());
    }

    @Override
    public void updateReply(String commentId, String updatedText) {
        updateReplyUseCase.setUpdatedText(updatedText);
        updateReplyUseCase.setReplyId(commentId);
        updateReplyUseCase.execute(new UpdateReplySubscriber());
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


    private final class UpdateReplySubscriber extends DefaultSubscriber<ReplyModel> {
        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxErrUpdate", e.getLocalizedMessage());
        }

        @Override
        public void onNext(ReplyModel replyModel) {
            super.onNext(replyModel);

            if (view != null) {
                view.onReplyUpdated(replyModel);
            }
        }
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

    private final class RetrieveCommentsSubscriber extends DefaultSubscriber<MergedCommentsModel> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            i("onxCommentsError", e.getLocalizedMessage());
        }

        @Override
        public void onNext(MergedCommentsModel mergedModel) {
            super.onNext(mergedModel);

            if (view != null) {
                view.displayComments(mergedModel.getThreadCommentModels(),
                        mergedModel.getUserIdentifier(),
                        mergedModel.getVideoStatsModel().getCommentCount()
                );
            }
        }
    }


    private final class DeletionSubscriber extends DefaultSubscriber<String> {

        @Override
        public void onNext(String deletedCommentId) {
            super.onNext(deletedCommentId);

            if (view != null) {
                view.onCommentDeleted(deletedCommentId);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxDeletionErr", e.getLocalizedMessage());
        }
    }

    private final class SpamSubscriber extends DefaultSubscriber<String> {

        @Override
        public void onNext(String markedCommentId) {
            super.onNext(markedCommentId);

            if (view != null) {
                view.onMarkedAsSpam(markedCommentId);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxSpamErr", e.getLocalizedMessage());
        }
    }

    private final class UpdateThreadSubscriber extends DefaultSubscriber<ThreadCommentModel> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxUpdateErr", e.getLocalizedMessage());
        }

        @Override
        public void onNext(ThreadCommentModel threadCommentModel) {
            super.onNext(threadCommentModel);

            if (view != null) {
                view.onCommentUpdated(threadCommentModel);
            }
        }
    }
}
