package com.example.scame.lighttubex.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.scame.lighttubex.PrivateValues;
import com.example.scame.lighttubex.data.entities.videolist.VideoEntityList;
import com.example.scame.lighttubex.data.mappers.VideoListMapper;
import com.example.scame.lighttubex.data.rest.VideoListApi;
import com.example.scame.lighttubex.presentation.LightTubeApp;
import com.example.scame.lighttubex.presentation.model.VideoItemModel;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

public class VideoListDataManagerImp implements IVideoListDataManager {

    private Retrofit retrofit;
    private VideoListMapper mapper;
    private SharedPreferences sp;

    public VideoListDataManagerImp() {
        retrofit = LightTubeApp.getAppComponent().getRetrofit();
        mapper = new VideoListMapper();
        Context context = LightTubeApp.getAppComponent().getApp();
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }


    @Override
    public Observable<List<VideoItemModel>> getVideoItemsList(int page) {
        String nextPageToken = getNextPageToken(page);
        savePageNumber(page);

        // temporary solution
        VideoListApi videoListApi = retrofit.create(VideoListApi.class);
        return videoListApi
                .getVideoList(nextPageToken, "mostPopular", PrivateValues.API_KEY, "snippet", 25)
                .doOnNext(this::saveNextPageToken)
                .map(mapper::convert);
    }

    private void saveNextPageToken(VideoEntityList entityList) {
        String nextPage = entityList.getNextPageToken();
        sp.edit().putString("nextPageToken", nextPage).apply();
    }

    private void savePageNumber(int page) {
        sp.edit().putInt("pageNumber", page).apply();
    }

    private String getNextPageToken(int page) {
        if (getPageNumber() > page) {
            return null;
        }
        return sp.getString("nextPageToken", null);
    }

    private int getPageNumber() {
        return sp.getInt("pageNumber", 0);
    }
}
