package com.example.scame.lighttube.domain.usecases;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionsEntity;
import com.example.scame.lighttube.data.repository.IRecentVideosDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.LightTubeApp;

import rx.Observable;

public class SubscriptionsUseCase extends UseCase<SubscriptionsEntity> {

    private IRecentVideosDataManager recentVideosDataManager;

    public SubscriptionsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                IRecentVideosDataManager recentVideosDataManager) {

        super(subscribeOn, observeOn);
        this.recentVideosDataManager = recentVideosDataManager;
    }

    @Override
    protected Observable<SubscriptionsEntity> getUseCaseObservable() {
        return recentVideosDataManager.getSubscriptions()
                .doOnNext(subscriptionsEntity -> saveSubscriptionsNumber(subscriptionsEntity.getItems().size()));
    }

    private void saveSubscriptionsNumber(int subscriptions) {
        Context context = LightTubeApp.getAppComponent().getApp();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPrefs.edit().putInt(SubscriptionsUseCase.class.getCanonicalName(), subscriptions).apply();
    }
}
