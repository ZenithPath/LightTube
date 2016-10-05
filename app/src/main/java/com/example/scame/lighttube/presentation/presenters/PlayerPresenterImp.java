package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.RateVideoUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveCommentsUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveRatingUseCase;
import com.example.scame.lighttube.presentation.model.CommentListModel;
import com.example.scame.lighttube.presentation.model.RatingModel;

public class PlayerPresenterImp<T extends IPlayerPresenter.PlayerView> implements IPlayerPresenter<T> {

    private T view;

    private RetrieveRatingUseCase retrieveRatingUseCase;

    private RateVideoUseCase rateVideoUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private RetrieveCommentsUseCase retrieveCommentsUseCase;

    public PlayerPresenterImp(RetrieveRatingUseCase retrieveRatingUseCase,
                              RateVideoUseCase rateVideoUseCase,
                              RetrieveCommentsUseCase retrieveCommentsUseCase,
                              SubscriptionsHandler subscriptionsHandler) {

        this.retrieveCommentsUseCase = retrieveCommentsUseCase;
        this.retrieveRatingUseCase = retrieveRatingUseCase;
        this.rateVideoUseCase = rateVideoUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void getVideoRating(String videoId) {
        retrieveRatingUseCase.setVideoId(videoId);
        retrieveRatingUseCase.execute(new RetrieveRatingSubscriber());
    }

    @Override
    public void rateVideo(String videoId, String rating) {
        rateVideoUseCase.setVideoId(videoId);
        rateVideoUseCase.setRating(rating);
        rateVideoUseCase.execute(new RateVideoSubscriber());
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

    private final class RetrieveRatingSubscriber extends DefaultSubscriber<RatingModel> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxRetrievedError", e.getLocalizedMessage());
        }

        @Override
        public void onNext(RatingModel ratingModel) {
            super.onNext(ratingModel);

            view.displayRating(ratingModel.getRating());
        }
    }

    private final class RateVideoSubscriber extends DefaultSubscriber<Void> {

        @Override
        public void onCompleted() {
            super.onCompleted();

            Log.i("onxCompleted", "rated");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxErrorRated", e.getLocalizedMessage());
        }
    }
}
