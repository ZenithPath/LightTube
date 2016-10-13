package com.example.scame.lighttube.data.di;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.scame.lighttube.data.mappers.CategoryPairsMapper;
import com.example.scame.lighttube.data.mappers.CommentListMapper;
import com.example.scame.lighttube.data.mappers.DurationsCombiner;
import com.example.scame.lighttube.data.mappers.IdsMapper;
import com.example.scame.lighttube.data.mappers.PublishingDateParser;
import com.example.scame.lighttube.data.mappers.RatingMapper;
import com.example.scame.lighttube.data.mappers.RecentVideosMapper;
import com.example.scame.lighttube.data.mappers.ReplyListMapper;
import com.example.scame.lighttube.data.mappers.ReplyRequestBuilder;
import com.example.scame.lighttube.data.mappers.ReplyRequestMapper;
import com.example.scame.lighttube.data.mappers.SearchListMapper;
import com.example.scame.lighttube.data.mappers.ThreadRequestBuilder;
import com.example.scame.lighttube.data.mappers.ThreadRequestMapper;
import com.example.scame.lighttube.data.mappers.VideoListMapper;
import com.example.scame.lighttube.data.repository.AccountDataManagerImp;
import com.example.scame.lighttube.data.repository.CategoryDataManagerImp;
import com.example.scame.lighttube.data.repository.ChannelVideosDataManagerImp;
import com.example.scame.lighttube.data.repository.CommentsDataManagerImp;
import com.example.scame.lighttube.data.repository.ContentDetailsDataManagerImp;
import com.example.scame.lighttube.data.repository.IAccountDataManager;
import com.example.scame.lighttube.data.repository.ICategoryDataManager;
import com.example.scame.lighttube.data.repository.IChannelVideosDataManager;
import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.data.repository.IContentDetailsDataManager;
import com.example.scame.lighttube.data.repository.IRatingDataManager;
import com.example.scame.lighttube.data.repository.IRecentVideosDataManager;
import com.example.scame.lighttube.data.repository.ISearchDataManager;
import com.example.scame.lighttube.data.repository.IVideoListDataManager;
import com.example.scame.lighttube.data.repository.RatingDataManagerImp;
import com.example.scame.lighttube.data.repository.RecentVideosDataManagerImp;
import com.example.scame.lighttube.data.repository.SearchDataManagerImp;
import com.example.scame.lighttube.data.repository.VideoListDataManagerImp;
import com.example.scame.lighttube.data.rest.CommentsApi;
import com.example.scame.lighttube.data.rest.RatingApi;
import com.example.scame.lighttube.data.rest.RecentVideosApi;
import com.example.scame.lighttube.data.rest.SearchApi;
import com.example.scame.lighttube.data.rest.VideoListApi;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataManagersModule {

    @Singleton
    @Provides
    IAccountDataManager provideAccountDataManager(SharedPreferences sharedPrefs, Context context) {
        return new AccountDataManagerImp(sharedPrefs, context);
    }

    @Singleton
    @Provides
    IVideoListDataManager provideVideoListDataManager(VideoListMapper mapper, VideoListApi videoListApi,
                                                      SharedPreferences sharedPrefs, Context context) {

        return new VideoListDataManagerImp(mapper, videoListApi, context, sharedPrefs);
    }

    @Singleton
    @Provides
    ISearchDataManager provideSearchDataManager(SearchApi searchApi, SearchListMapper searchListMapper) {
        return new SearchDataManagerImp(searchApi, searchListMapper);
    }

    @Singleton
    @Provides
    ICategoryDataManager provideCategoryDataManager(SharedPreferences sharedPrefs, Context context, SearchApi searchApi,
                                                    CategoryPairsMapper mapper, @Named("category") List<String> categoryKeys,
                                                    ISearchDataManager searchDataManager, SearchListMapper searchListMapper) {

        return new CategoryDataManagerImp(sharedPrefs, context, searchApi, mapper, categoryKeys,
                                                            searchDataManager, searchListMapper);
    }

    @Singleton
    @Provides
    IRecentVideosDataManager provideRecentVideosDataManager(RecentVideosApi recentVideosApi, RecentVideosMapper mapper,
                                                            PublishingDateParser parser, SharedPreferences sharedPrefs) {

        return new RecentVideosDataManagerImp(recentVideosApi, mapper, parser, sharedPrefs);
    }

    @Singleton
    @Provides
    IChannelVideosDataManager provideChannelVideosRepository(RecentVideosApi recentVideosApi, SharedPreferences sharedPrefs,
                                                             Context context, SearchListMapper searchListMapper) {

        return new ChannelVideosDataManagerImp(recentVideosApi, sharedPrefs, context, searchListMapper);
    }

    @Singleton
    @Provides
    IContentDetailsDataManager provideContentDetailsManager(VideoListApi videoListApi, IdsMapper idsMapper,
                                                            DurationsCombiner combiner) {

        return new ContentDetailsDataManagerImp(videoListApi, idsMapper, combiner);
    }

    @Singleton
    @Provides
    IRatingDataManager provideRatingDataManager(RatingApi ratingApi, RatingMapper ratingMapper) {
        return new RatingDataManagerImp(ratingApi, ratingMapper);
    }

    @Singleton
    @Provides
    ICommentsDataManager provideCommentsDataManager(CommentsApi commentsApi, CommentListMapper commentListMapper,
                                                    ReplyListMapper replyListMapper, ThreadRequestMapper threadMapper,
                                                    ThreadRequestBuilder commentBuilder, ReplyRequestBuilder replyBuilder,
                                                    ReplyRequestMapper replyMapper) {
        return new CommentsDataManagerImp(commentsApi, commentListMapper, replyListMapper,
                threadMapper, commentBuilder, replyBuilder, replyMapper);
    }
}
