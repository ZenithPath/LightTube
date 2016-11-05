package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.data.repository.CommentsDataManagerImp;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.DeleteCommentUseCase;
import com.example.scame.lighttube.domain.usecases.FooterInitializationUseCase;
import com.example.scame.lighttube.domain.usecases.MarkAsSpamUseCase;
import com.example.scame.lighttube.domain.usecases.PostThreadCommentUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveCommentsUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateReplyUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateThreadUseCase;
import com.example.scame.lighttube.presentation.model.MergedCommentsModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentsWrapper;

import static android.util.Log.i;

public class PlayerFooterPresenterImp<T extends IPlayerFooterPresenter.FooterView> implements IPlayerFooterPresenter<T> {

    private FooterInitializationUseCase footerInitializationUseCase;

    private DeleteCommentUseCase deleteCommentUseCase;

    private MarkAsSpamUseCase markAsSpamUseCase;

    private UpdateThreadUseCase updateThreadUseCase;

    private PostThreadCommentUseCase postCommentUseCase;

    private UpdateReplyUseCase updateReplyUseCase;

    private RetrieveCommentsUseCase retrieveCommentsUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private T view;

    public PlayerFooterPresenterImp(FooterInitializationUseCase footerInitializationUseCase,
                                    DeleteCommentUseCase deleteCommentUseCase,
                                    MarkAsSpamUseCase markAsSpamUseCase,
                                    UpdateThreadUseCase updateThreadUseCase,
                                    PostThreadCommentUseCase postCommentUseCase,
                                    UpdateReplyUseCase updateReplyUseCase,
                                    RetrieveCommentsUseCase retrieveCommentsUseCase,
                                    SubscriptionsHandler subscriptionsHandler) {
        this.updateReplyUseCase = updateReplyUseCase;
        this.postCommentUseCase = postCommentUseCase;
        this.markAsSpamUseCase = markAsSpamUseCase;
        this.retrieveCommentsUseCase = retrieveCommentsUseCase;
        this.footerInitializationUseCase = footerInitializationUseCase;
        this.deleteCommentUseCase = deleteCommentUseCase;
        this.updateThreadUseCase = updateThreadUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void initializeFooter(String videoId, String order, int page) {
        footerInitializationUseCase.setPage(page);
        footerInitializationUseCase.setVideoId(videoId);
        footerInitializationUseCase.setOrder(order);
        footerInitializationUseCase.execute(new InitializationSubscriber());
    }

    @Override
    public void commentsOrderClick(String videoId, @CommentsDataManagerImp.CommentsOrders String previousOrder,
                                   @CommentsDataManagerImp.CommentsOrders String newOrder, int page) {
        if (!previousOrder.equals(newOrder)) {
            retrieveCommentsUseCase.setPage(page);
            retrieveCommentsUseCase.setVideoId(videoId);
            retrieveCommentsUseCase.setOrder(newOrder);
            retrieveCommentsUseCase.execute(new RetrieveCommentsSubscriber());
        }
    }

    @Override
    public void getCommentList(String videoId, @CommentsDataManagerImp.CommentsOrders String order, int page) {
        retrieveCommentsUseCase.setPage(page);
        retrieveCommentsUseCase.setVideoId(videoId);
        retrieveCommentsUseCase.setOrder(order);
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


    private final class InitializationSubscriber extends DefaultSubscriber<MergedCommentsModel> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxErr", e.getLocalizedMessage());
        }

        @Override
        public void onNext(MergedCommentsModel mergedCommentsModel) {
            super.onNext(mergedCommentsModel);

            if (view != null) {
                view.onInitialized(
                        mergedCommentsModel.getCommentsWrapper().getComments(),
                        mergedCommentsModel.getUserIdentifier(),
                        mergedCommentsModel.getCommentsWrapper().getCommentsOrder(),
                        mergedCommentsModel.getVideoStatsModel()
                );
            }
        }
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

    private final class RetrieveCommentsSubscriber extends DefaultSubscriber<ThreadCommentsWrapper> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            i("onxCommentsError", e.getLocalizedMessage());
        }

        @Override
        public void onNext(ThreadCommentsWrapper threadCommentsWrapper) {
            super.onNext(threadCommentsWrapper);

            if (view != null) {
                view.onCommentsUpdated(threadCommentsWrapper.getComments(), threadCommentsWrapper.getCommentsOrder());
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
