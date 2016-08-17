package com.example.scame.lighttube.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.entities.videolist.VideoEntityList;
import com.example.scame.lighttube.data.mappers.VideoListMapper;
import com.example.scame.lighttube.data.rest.VideoListApi;
import com.example.scame.lighttube.presentation.LightTubeApp;
import com.example.scame.lighttube.presentation.model.VideoItemModel;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

public class VideoListDataManagerImp implements IVideoListDataManager {

    private static final String CHART = "mostPopular";
    private static final String PART = "snippet";
    private static final int MAX_RESULTS = 25;

    @Override
    public Observable<List<VideoItemModel>> getVideoItemsList(int page) {
        String nextPageToken = getNextPageToken(page);
        Retrofit retrofit = LightTubeApp.getAppComponent().getRetrofit();
        VideoListMapper mapper = new VideoListMapper();

        VideoListApi videoListApi = retrofit.create(VideoListApi.class);
        return videoListApi
                .getVideoList(nextPageToken, CHART, PrivateValues.API_KEY, PART, MAX_RESULTS)
                .doOnNext(videoEntityList -> {
                    saveNextPageToken(videoEntityList);
                    savePageNumber(page);
                }).map(mapper::convert);
    }


    private void saveNextPageToken(VideoEntityList entityList) {
        getSharedPrefs().edit()
                .putString(getContext().getString(R.string.next_page_token_list),
                        entityList.getNextPageToken()).apply();
    }


    private String getNextPageToken(int page) {
        String nextPageToken = getSharedPrefs()
                .getString(getContext().getString(R.string.next_page_token_list), null);
        int prevPageNumber = getSharedPrefs()
                .getInt(getContext().getString(R.string.list_page_number), 0);

        return page < prevPageNumber ? null : nextPageToken;
    }

    private void savePageNumber(int page) {
        getSharedPrefs().edit().putInt(getContext().getString(R.string.list_page_number), page).apply();
    }

    private SharedPreferences getSharedPrefs() {
        Context context = LightTubeApp.getAppComponent().getApp();
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private Context getContext() {
        return LightTubeApp.getAppComponent().getApp();
    }
}
