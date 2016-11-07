package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.GetRatingUseCase;
import com.example.scame.lighttube.domain.usecases.RateVideoUseCase;
import com.example.scame.lighttube.presentation.model.RatingModel;

public class VideoRatingPresenterImp<T extends VideoRatingPresenter.PlayerView> implements VideoRatingPresenter<T> {

    private T view;

    private GetRatingUseCase getRatingUseCase;

    private RateVideoUseCase rateVideoUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    public VideoRatingPresenterImp(GetRatingUseCase getRatingUseCase,
                                   RateVideoUseCase rateVideoUseCase,
                                   SubscriptionsHandler subscriptionsHandler) {
        this.getRatingUseCase = getRatingUseCase;
        this.rateVideoUseCase = rateVideoUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void getVideoRating(String videoId) {
        getRatingUseCase.setVideoId(videoId);
        getRatingUseCase.execute(new RetrieveRatingSubscriber());
    }

    @Override
    public void rateVideo(String videoId, String rating) {
        rateVideoUseCase.setVideoId(videoId);
        rateVideoUseCase.setRating(rating);
        rateVideoUseCase.execute(new RateVideoSubscriber());
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
        view = null;
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

            if (view != null) {
                view.displayRating(ratingModel.getRating());
            }
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
