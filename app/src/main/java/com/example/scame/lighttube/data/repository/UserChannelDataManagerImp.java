package com.example.scame.lighttube.data.repository;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.rest.ChannelsApi;

import rx.Observable;

public class UserChannelDataManagerImp implements IUserChannelDataManager {

    private static final String USER_CHANNEL_PART = "snippet";

    private static final boolean USER_CHANNEL_MINE = true;

    private ChannelsApi channelsApi;

    private SharedPreferences sharedPrefs;

    private Context context;

    public UserChannelDataManagerImp(ChannelsApi channelsApi, SharedPreferences sharedPrefs, Context context) {
        this.channelsApi = channelsApi;
        this.sharedPrefs = sharedPrefs;
        this.context = context;
    }

    // TODO: check it
    // not sure if account can contain only one channel

    @Override
    public Observable<String> getUserChannelUrl() {
        return isChannelUrlCached() ? getFromCache() : getFromNetwork();
    }

    private Observable<String> getFromNetwork() {
        return channelsApi.getCurrentUserChannel(USER_CHANNEL_PART, USER_CHANNEL_MINE, PrivateValues.API_KEY)
                .map(channelEntity -> channelEntity.getItems().get(0).getId())
                .doOnNext(this::cacheUserChannelUrl);
    }


    private void cacheUserChannelUrl(String channelUrl) {
        sharedPrefs.edit().putString(context.getString(R.string.channel_url_key), channelUrl).apply();
    }

    private Observable<String> getFromCache() {
        String channelUrlKey = context.getString(R.string.channel_url_key);
        return Observable.just(sharedPrefs.getString(channelUrlKey, ""));
    }

    private boolean isChannelUrlCached() {
        String channelUrlKey = context.getString(R.string.channel_url_key);
        return !sharedPrefs.getString(channelUrlKey, "").equals("");
    }
}
