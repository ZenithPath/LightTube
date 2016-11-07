package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.GetHomeVideosUseCase;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;


public class HomePresenterImp<V extends HomePresenter.VideoListView>
                                                implements HomePresenter<V> {
    private static final int FIRST_PAGE = 0;

    private V view;

    private GetHomeVideosUseCase videosUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    public HomePresenterImp(GetHomeVideosUseCase videosUseCase, SubscriptionsHandler subscriptionsHandler) {
        this.videosUseCase = videosUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void setView(V view) {
        this.view = view;
    }

    @Override
    public void fetchVideos(int page) {
        videosUseCase.setPage(page);
        videosUseCase.execute(new VideoListSubscriber());
    }

    @Override
    public void resume() { }

    @Override
    public void pause() { }

    @Override
    public void destroy() {
        subscriptionsHandler.unsubscribe();
        view = null;
    }

    private final class VideoListSubscriber extends DefaultSubscriber<VideoModelsWrapper> {

        @Override
        public void onNext(VideoModelsWrapper modelsWrapper) {
            super.onNext(modelsWrapper);

            if (view != null) {
                if (modelsWrapper.getPage() == FIRST_PAGE) {
                    view.initializeAdapter(modelsWrapper.getVideoModels());
                } else {
                    view.updateAdapter(modelsWrapper.getVideoModels());
                }
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxVideoListErr", e.getLocalizedMessage());
        }
    }
}
