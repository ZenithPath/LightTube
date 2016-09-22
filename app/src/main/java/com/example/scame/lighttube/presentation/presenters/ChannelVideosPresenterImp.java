package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.domain.usecases.ChannelVideosUseCase;
import com.example.scame.lighttube.domain.usecases.ContentDetailsUseCase;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

public class ChannelVideosPresenterImp<T extends IChannelVideosPresenter.ChannelsView>
                                        implements IChannelVideosPresenter<T> {

    private static final int FIRST_PAGE = 0;

    private int currentPage;

    private T view;

    private ChannelVideosUseCase channelVideosUseCase;

    private ContentDetailsUseCase contentDetailsUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    public ChannelVideosPresenterImp(ChannelVideosUseCase channelVideosUseCase,
                                     ContentDetailsUseCase detailsUseCase,
                                     SubscriptionsHandler subscriptionsHandler) {

        this.channelVideosUseCase = channelVideosUseCase;
        this.contentDetailsUseCase = detailsUseCase;

        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void fetchChannelVideos(String channelId, int page) {
        currentPage = page;

        channelVideosUseCase.setPage(page);
        channelVideosUseCase.setChannelId(channelId);
        channelVideosUseCase.execute(new ChannelsSubscriber());
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

    private final class ChannelsSubscriber extends DefaultSubscriber<List<VideoModel>> {

        @Override
        public void onNext(List<VideoModel> videoModels) {
            super.onNext(videoModels);

            contentDetailsUseCase.setVideoModels(videoModels);
            contentDetailsUseCase.execute(new ContentDetailsSubscriber());
        }
    }

    private final class ContentDetailsSubscriber extends DefaultSubscriber<List<VideoModel>> {

        @Override
        public void onNext(List<VideoModel> videoModels) {
            super.onNext(videoModels);

            if (currentPage == FIRST_PAGE) {
                view.initializeAdapter(videoModels);
            } else {
                view.updateAdapter(videoModels);
            }
        }
    }
}
