package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.GetGridVideosUseCase;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;


public class GridPresenterImp<V extends GridPresenter.GridView> implements GridPresenter<V> {

    private static final int FIRST_PAGE = 0;

    private V view;

    private GetGridVideosUseCase gridUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    public GridPresenterImp(GetGridVideosUseCase gridUseCase, SubscriptionsHandler subscriptionsHandler) {
        this.subscriptionsHandler = subscriptionsHandler;
        this.gridUseCase = gridUseCase;
    }

    @Override
    public void setView(V view) {
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

    @Override
    public void fetchVideos(String category, String duration, int page) {
        gridUseCase.setCategory(category);
        gridUseCase.setDuration(duration);
        gridUseCase.setPage(page);

        gridUseCase.execute(new GridSubscriber());
    }

    private final class GridSubscriber extends DefaultSubscriber<VideoModelsWrapper> {

        @Override
        public void onNext(VideoModelsWrapper videoModelsWrapper) {
            super.onNext(videoModelsWrapper);

            if (view != null) {
                if (videoModelsWrapper.getPage() == FIRST_PAGE) {
                    view.initializeAdapter(videoModelsWrapper.getVideoModels());
                } else {
                    view.updateAdapter(videoModelsWrapper.getVideoModels());
                }
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxGridErr", e.getLocalizedMessage());
        }
    }
}
