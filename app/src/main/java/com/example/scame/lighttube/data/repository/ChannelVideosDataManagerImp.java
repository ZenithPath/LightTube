package com.example.scame.lighttube.data.repository;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.mappers.SearchListMapper;
import com.example.scame.lighttube.data.rest.RecentVideosApi;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public class ChannelVideosDataManagerImp implements IChannelVideosDataManager {

    private static final int MAX_RESULTS_SEARCH = 5;
    private static final String PART = "snippet";
    private static final String ORDER = "date";
    private static final String TYPE = "video";

    private RecentVideosApi recentVideosApi;
    private SharedPreferences sharedPrefs;

    private SearchListMapper searchListMapper;

    private Context context;

    public ChannelVideosDataManagerImp(RecentVideosApi recentVideosApi, SharedPreferences sharedPrefs,
                                       Context context, SearchListMapper searchListMapper) {

        this.searchListMapper = searchListMapper;
        this.recentVideosApi = recentVideosApi;
        this.sharedPrefs = sharedPrefs;
        this.context = context;
    }

    @Override
    public Observable<List<VideoModel>> getChannelVideos(String channelId, int page) {
        String nextPageToken = getNextPageToken(page);

        return recentVideosApi.getRecentVideos(PART, MAX_RESULTS_SEARCH, channelId, ORDER, nextPageToken, TYPE)
                .doOnNext(searchEntity -> {
                        saveNextPageToken(searchEntity);
                        savePageNumber(page);
                }).map(searchListMapper::convert);
    }

    private void saveNextPageToken(SearchEntity searchEntity) {
        sharedPrefs.edit().putString(context.getString(R.string.next_page_token_list),
                searchEntity.getNextPageToken()).apply();
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
