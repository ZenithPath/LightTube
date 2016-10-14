package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.RateVideoUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveRatingUseCase;
import com.example.scame.lighttube.presentation.model.RatingModel;

public class VideoRatingPresenterImp<T extends IVideoRatingPresenter.PlayerView> implements IVideoRatingPresenter<T> {

    private T view;

    private RetrieveRatingUseCase retrieveRatingUseCase;

    private RateVideoUseCase rateVideoUseCase;

    private SubscriptionsHandler subscriptionsHandler;



    public VideoRatingPresenterImp(RetrieveRatingUseCase retrieveRatingUseCase,
                                   RateVideoUseCase rateVideoUseCase,
                                   SubscriptionsHandler subscriptionsHandler) {

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

        @Override
        public void onCompleted() {
            super.onCompleted();

            Log.i("onxRatingComp", "true");
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
