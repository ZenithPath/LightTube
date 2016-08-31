package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionsEntity;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.SubscriptionsUseCase;

public class RecentVideosPresenterImp<T extends IRecentVideosPresenter.RecentVideosView>
                                            implements IRecentVideosPresenter<T> {

    private SubscriptionsUseCase subscriptionsUseCase;

    private T view;

    public RecentVideosPresenterImp(SubscriptionsUseCase subscriptionsUseCase) {
        this.subscriptionsUseCase = subscriptionsUseCase;
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

    private final class SubscriptionsSubscriber extends DefaultSubscriber<SubscriptionsEntity> {

        @Override
        public void onNext(SubscriptionsEntity subscriptionsEntity) {
            super.onNext(subscriptionsEntity);

            Log.i("onxNext", subscriptionsEntity.getItems().get(0).getSnippet().getTitle());
        }
    }
}
