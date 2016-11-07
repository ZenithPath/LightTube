package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.GetChannelVideosUseCase;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;


public class ChannelVideosPresenterImp<T extends ChannelVideosPresenter.ChannelsView>
                                        implements ChannelVideosPresenter<T> {

    private static final int FIRST_PAGE = 0;

    private T view;

    private GetChannelVideosUseCase getChannelVideosUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    public ChannelVideosPresenterImp(GetChannelVideosUseCase getChannelVideosUseCase,
                                     SubscriptionsHandler subscriptionsHandler) {
        this.getChannelVideosUseCase = getChannelVideosUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void fetchChannelVideos(String channelId, int page) {
        getChannelVideosUseCase.setPage(page);
        getChannelVideosUseCase.setChannelId(channelId);
        getChannelVideosUseCase.execute(new ChannelsSubscriber());
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

    private final class ChannelsSubscriber extends DefaultSubscriber<VideoModelsWrapper> {

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
            Log.i("onxChannelVideosErr", e.getLocalizedMessage());
        }
    }
}
