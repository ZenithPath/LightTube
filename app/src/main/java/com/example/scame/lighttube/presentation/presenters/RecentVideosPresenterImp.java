package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.mappers.SearchListMapper;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.RecentVideosUseCase;
import com.example.scame.lighttube.domain.usecases.SubscriptionsUseCase;
import com.example.scame.lighttube.presentation.model.SearchItemModel;

import java.util.ArrayList;
import java.util.List;

public class RecentVideosPresenterImp<T extends IRecentVideosPresenter.RecentVideosView>
                                            implements IRecentVideosPresenter<T> {

    private SubscriptionsUseCase subscriptionsUseCase;

    private RecentVideosUseCase recentVideosUseCase;

    private List<SearchEntity> searchEntities;

    private int subscriptionsNumber;
    private int subscriptionsCounter;

    private T view;

    public RecentVideosPresenterImp(SubscriptionsUseCase subscriptionsUseCase,
                                    RecentVideosUseCase recentVideosUseCase) {

        this.subscriptionsUseCase = subscriptionsUseCase;
        this.recentVideosUseCase = recentVideosUseCase;
    }

    @Override
    public void fetchRecentVideos() {
        subscriptionsUseCase.execute(new SubscriptionsSubscriber());

        // TODO: search videos by channels Ids & filter the most recent
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

            // when all threads are done, combine & map search results
            // TODO: implement use case to do that work
            if (++subscriptionsCounter == subscriptionsNumber) {
                List<SearchItemModel> searchItems = new ArrayList<>();
                SearchListMapper mapper = new SearchListMapper();

                for (SearchEntity searchEntity : searchEntities) {
                    searchItems.addAll(mapper.convert(searchEntity));
                }

                view.populateAdapter(searchItems);
            }
        }
    }
}
