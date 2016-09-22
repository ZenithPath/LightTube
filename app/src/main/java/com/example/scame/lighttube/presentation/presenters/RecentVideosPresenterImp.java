package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionsEntity;
import com.example.scame.lighttube.data.mappers.ChannelsMapper;
import com.example.scame.lighttube.data.mappers.SubscriptionsIdsMapper;
import com.example.scame.lighttube.domain.usecases.ContentDetailsUseCase;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.OrderByDateUseCase;
import com.example.scame.lighttube.domain.usecases.RecentVideosUseCase;
import com.example.scame.lighttube.domain.usecases.SubscriptionsUseCase;
import com.example.scame.lighttube.presentation.model.ChannelModel;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class RecentVideosPresenterImp<T extends IRecentVideosPresenter.RecentVideosView>
                                            implements IRecentVideosPresenter<T> {

    private SubscriptionsUseCase subscriptionsUseCase;

    private RecentVideosUseCase recentVideosUseCase;

    private OrderByDateUseCase orderUseCase;

    private ContentDetailsUseCase contentDetailsUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private List<SearchEntity> searchEntities;

    private int subscriptionsNumber;
    private int subscriptionsCounter;

    private T view;

    public RecentVideosPresenterImp(SubscriptionsUseCase subscriptionsUseCase,
                                    RecentVideosUseCase recentVideosUseCase,
                                    ContentDetailsUseCase contentDetailsUseCase,
                                    OrderByDateUseCase orderUseCase,
                                    SubscriptionsHandler subscriptionsHandler) {

        this.contentDetailsUseCase = contentDetailsUseCase;
        this.subscriptionsUseCase = subscriptionsUseCase;
        this.recentVideosUseCase = recentVideosUseCase;
        this.orderUseCase = orderUseCase;

        this.subscriptionsHandler = subscriptionsHandler;
    }


    // get a list of subscriptions (channels)
    @Override
    public void initialize() {
        subscriptionsCounter = 0; // presenter is a singleton, so we must set a counter to zero
        subscriptionsUseCase.execute(new SubscriptionsSubscriber());
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

    private final class SubscriptionsSubscriber extends DefaultSubscriber<SubscriptionsEntity> {

        private SubscriptionsIdsMapper subscriptionsIdsMapper = new SubscriptionsIdsMapper();
        private ChannelsMapper channelsMapper = new ChannelsMapper();

        @Override
        public void onNext(SubscriptionsEntity subscriptionsEntity) {
            super.onNext(subscriptionsEntity);

            makeRecentVideosRequests(subscriptionsEntity);
            visualizeChannelList(subscriptionsEntity);
        }

        private void makeRecentVideosRequests(SubscriptionsEntity subscriptionsEntity) {

            List<String> subscriptionsIds = subscriptionsIdsMapper.convert(subscriptionsEntity);

            subscriptionsNumber = subscriptionsIds.size();
            searchEntities = new ArrayList<>();

            // to bypass unreusability of CompositeSubscription
            recentVideosUseCase.createSubscriptiosHolder();

            // make concurrent searches on each channel
            for (String channelId : subscriptionsIds) {
                recentVideosUseCase.setChannelId(channelId);
                recentVideosUseCase.execute(new RecentVideosSubscriber());
            }
        }

        private void visualizeChannelList(SubscriptionsEntity subscriptionsEntity) {
            List<ChannelModel> channelModels = channelsMapper.convert(subscriptionsEntity);
            view.visualizeChannelsList(channelModels);
        }
    }

    private final class RecentVideosSubscriber extends DefaultSubscriber<SearchEntity> {

        @Override
        public void onNext(SearchEntity searchEntity) {
            super.onNext(searchEntity);

            searchEntities.add(searchEntity);
        }

        @Override
        public void onCompleted() {
            super.onCompleted();

            // when all threads are done, combine & sort search results by publishing date
            if (++subscriptionsCounter == subscriptionsNumber) {
                orderUseCase.setSearchEntities(searchEntities);
                orderUseCase.execute(new OrderSubscriber());
            }
         }
    }

    private final class OrderSubscriber extends DefaultSubscriber<List<VideoModel>> {

        @Override
        public void onNext(List<VideoModel> videoModels) {
            super.onNext(videoModels);

            // thanks to YouTube data API we need one more request to get duration of videos
            contentDetailsUseCase.setVideoModels(videoModels);
            contentDetailsUseCase.execute(new ContentDetailsSubscriber());
        }
    }


    private final class ContentDetailsSubscriber extends DefaultSubscriber<List<VideoModel>> {

        @Override
        public void onNext(List<VideoModel> videoModels) {
            super.onNext(videoModels);

            // finally we can display the result
            view.populateAdapter(videoModels);
        }
    }
}
