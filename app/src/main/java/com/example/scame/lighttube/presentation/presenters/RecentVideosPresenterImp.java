package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.OrderByDateUseCase;
import com.example.scame.lighttube.domain.usecases.RecentVideosUseCase;
import com.example.scame.lighttube.domain.usecases.SubscriptionsUseCase;
import com.example.scame.lighttube.presentation.model.SearchItemModel;

import java.util.ArrayList;
import java.util.List;

public class RecentVideosPresenterImp<T extends IRecentVideosPresenter.RecentVideosView>
                                            implements IRecentVideosPresenter<T> {

    private SubscriptionsUseCase subscriptionsUseCase;

    private RecentVideosUseCase recentVideosUseCase;

    private OrderByDateUseCase orderUseCase;

    private List<SearchEntity> searchEntities;

    private int subscriptionsNumber;
    private int subscriptionsCounter;

    private T view;

    public RecentVideosPresenterImp(SubscriptionsUseCase subscriptionsUseCase,
                                    RecentVideosUseCase recentVideosUseCase,
                                    OrderByDateUseCase orderUseCase) {

        this.subscriptionsUseCase = subscriptionsUseCase;
        this.recentVideosUseCase = recentVideosUseCase;
        this.orderUseCase = orderUseCase;
    }

    @Override
    public void fetchRecentVideos() {
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

    }

    private final class SubscriptionsSubscriber extends DefaultSubscriber<List<String>> {

        @Override
        public void onNext(List<String> subscriptionsIds) {
            super.onNext(subscriptionsIds);

            subscriptionsNumber = subscriptionsIds.size();
            searchEntities = new ArrayList<>();

            // make concurrent searches on each channel
            for (String channelId : subscriptionsIds) {
                recentVideosUseCase.setChannelId(channelId);
                recentVideosUseCase.execute(new RecentVideosSubscriber());
            }
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

    private final class OrderSubscriber extends DefaultSubscriber<List<SearchItemModel>> {

        @Override
        public void onNext(List<SearchItemModel> searchItems) {
            super.onNext(searchItems);

            view.populateAdapter(searchItems);
        }
    }
}
