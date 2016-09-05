package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.ChannelVideosUseCase;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.presentation.model.SearchItemModel;

import java.util.List;

public class ChannelsPresenterImp<T extends IChannelsPresenter.ChannelsView>
                                        implements IChannelsPresenter<T> {

    private T view;

    private int page;

    private String channelId;

    private ChannelVideosUseCase channelVideosUseCase;

    public ChannelsPresenterImp(ChannelVideosUseCase channelVideosUseCase) {
        this.channelVideosUseCase = channelVideosUseCase;
    }

    @Override
    public void fetchChannelVideos(String channelId) {
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

    }

    public void setPage(int page) {
        this.page = page;
    }


    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    private final class ChannelsSubscriber extends DefaultSubscriber<List<SearchItemModel>> {

        @Override
        public void onNext(List<SearchItemModel> channelVideosModels) {
            super.onNext(channelVideosModels);

            view.populateAdapter(channelVideosModels);
        }
    }
}
