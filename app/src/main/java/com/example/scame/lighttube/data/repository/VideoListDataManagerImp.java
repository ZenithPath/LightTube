package com.example.scame.lighttube.data.repository;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.entities.videolist.VideoEntityList;
import com.example.scame.lighttube.data.mappers.VideoListMapper;
import com.example.scame.lighttube.data.rest.VideoListApi;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public class VideoListDataManagerImp implements IVideoListDataManager {

    private static final String CHART = "mostPopular";
    private static final String PART = "snippet";
    private static final int MAX_RESULTS = 10;

    private VideoListMapper videoListMapper;
    private VideoListApi videoListApi;

    private SharedPreferences sharedPrefs;

    private Context context;

    public VideoListDataManagerImp(VideoListMapper videoListMapper, VideoListApi videoListApi, Context context,
                                   SharedPreferences sharedPrefs) {

        this.videoListMapper = videoListMapper;
        this.videoListApi = videoListApi;
        this.sharedPrefs = sharedPrefs;
        this.context = context;
    }

    @Override
    public Observable<List<VideoModel>> getVideoItemsList(int page) {
        String nextPageToken = getNextPageToken(page);

        return videoListApi
                .getVideoList(nextPageToken, CHART, PrivateValues.API_KEY, PART, MAX_RESULTS)
                .doOnNext(videoEntityList -> {
                    saveNextPageToken(videoEntityList);
                    savePageNumber(page);
                }).map(videoListMapper::convert);
    }


    private void saveNextPageToken(VideoEntityList entityList) {
        sharedPrefs.edit()
                .putString(context.getString(R.string.next_page_token_list),
                        entityList.getNextPageToken()).apply();
    }


    private String getNextPageToken(int page) {
        String nextPageToken = sharedPrefs.getString(context.getString(R.string.next_page_token_list), null);
        int prevPageNumber = sharedPrefs.getInt(context.getString(R.string.list_page_number), 0);

        return page < prevPageNumber ? null : nextPageToken;
    }

    private void savePageNumber(int page) {
        sharedPrefs.edit().putInt(context.getString(R.string.list_page_number), page).apply();
    }
}
