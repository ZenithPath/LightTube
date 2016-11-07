package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.GetRecentVideosUseCase;
import com.example.scame.lighttube.domain.usecases.GetSubscriptionsUseCase;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.presentation.model.ChannelModel;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import java.util.List;


public class RecentVideosPresenterImp<T extends RecentVideosPresenter.RecentVideosView>
                                            implements RecentVideosPresenter<T> {

    private static final int FIRST_PAGE = 0;

    private GetRecentVideosUseCase getRecentVideosUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private GetSubscriptionsUseCase channelsUseCase;

    private T view;

    public RecentVideosPresenterImp(GetRecentVideosUseCase getRecentVideosUseCase,
                                    GetSubscriptionsUseCase channelsUseCase,
                                    SubscriptionsHandler subscriptionsHandler) {
        this.getRecentVideosUseCase = getRecentVideosUseCase;
        this.channelsUseCase = channelsUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void getChannelsList() {
        channelsUseCase.execute(new ChannelsSubscriber());
    }

    @Override
    public void getRecentVideosList() {
        getRecentVideosUseCase.execute(new RecentVideosSubscriber());
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

    private final class ChannelsSubscriber extends DefaultSubscriber<List<ChannelModel>> {

        @Override
        public void onNext(List<ChannelModel> channelModels) {
            super.onNext(channelModels);

            if (view != null) {
                view.visualizeChannelsList(channelModels);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxChannelsErr", e.getLocalizedMessage());
        }
    }

    private final class RecentVideosSubscriber extends DefaultSubscriber<VideoModelsWrapper> {

        @Override
        public void onNext(VideoModelsWrapper videoModelsWrapper) {
            super.onNext(videoModelsWrapper);

            if (view != null) {
                if (videoModelsWrapper.getPage() == FIRST_PAGE) {
                    view.populateAdapter(videoModelsWrapper.getVideoModels());
                } else {
                    view.updateAdapter(videoModelsWrapper.getVideoModels());
                }
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxRecentVideosErr", e.getLocalizedMessage());
        }
    }
}
