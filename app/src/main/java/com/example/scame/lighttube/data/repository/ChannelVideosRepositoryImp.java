package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.R;
import com.example.scame.lighttube.data.mappers.SearchListMapper;
import com.example.scame.lighttube.data.rest.RecentVideosApi;
import com.example.scame.lighttube.presentation.LightTubeApp;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import javax.inject.Inject;

import rx.Observable;

public class ChannelVideosRepositoryImp implements ChannelVideosRepository {

    private static final int MAX_RESULTS_SEARCH = 5;
    private static final String PART = "snippet";
    private static final String ORDER = "date";
    private static final String TYPE = "video";

    @Inject
    PaginationUtility paginationUtility;

    private ContentDetailsRepository detailsDataManager;

    private RecentVideosApi recentVideosApi;

    private SearchListMapper searchListMapper;

    public ChannelVideosRepositoryImp(RecentVideosApi recentVideosApi, SearchListMapper searchListMapper,
                                      ContentDetailsRepository detailsDataManager) {
        this.searchListMapper = searchListMapper;
        this.recentVideosApi = recentVideosApi;
        this.detailsDataManager = detailsDataManager;

        LightTubeApp.getAppComponent().inject(this);
        paginationUtility.setTokenStringId(R.string.next_page_general);
        paginationUtility.setPageStringId(R.string.page_number_general);
    }

    @Override
    public Observable<VideoModelsWrapper> getChannelVideos(String channelId, int page) {
        return recentVideosApi.getRecentVideos(PART, MAX_RESULTS_SEARCH, channelId, ORDER,
                paginationUtility.getNextPageToken(page), TYPE)
                .doOnNext(searchEntity -> {
                    paginationUtility.saveNextPageToken(searchEntity.getNextPageToken());
                    paginationUtility.saveCurrentPage(page);
                })
                .map(searchEntity -> searchListMapper.convert(searchEntity, page))
                .flatMap(detailsDataManager::getContentDetails);
    }
}
