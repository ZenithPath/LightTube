package com.example.scame.lighttube.data.di;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.scame.lighttube.data.mappers.AutocompleteDeserializer;
import com.example.scame.lighttube.data.mappers.CategoryPairsMapper;
import com.example.scame.lighttube.data.mappers.ChannelsMapper;
import com.example.scame.lighttube.data.mappers.CommentListMapper;
import com.example.scame.lighttube.data.mappers.DurationsCombiner;
import com.example.scame.lighttube.data.mappers.IdsMapper;
import com.example.scame.lighttube.data.mappers.PublishingDateParser;
import com.example.scame.lighttube.data.mappers.RatingMapper;
import com.example.scame.lighttube.data.mappers.RecentVideosMapper;
import com.example.scame.lighttube.data.mappers.ReplyListMapper;
import com.example.scame.lighttube.data.mappers.ReplyPostBuilder;
import com.example.scame.lighttube.data.mappers.ReplyResponseMapper;
import com.example.scame.lighttube.data.mappers.ReplyUpdateBuilder;
import com.example.scame.lighttube.data.mappers.SearchListMapper;
import com.example.scame.lighttube.data.mappers.ThreadPostBuilder;
import com.example.scame.lighttube.data.mappers.ThreadResponseMapper;
import com.example.scame.lighttube.data.mappers.ThreadUpdateBuilder;
import com.example.scame.lighttube.data.mappers.HomeVideosMapper;
import com.example.scame.lighttube.data.mappers.VideoStatsMapper;
import com.example.scame.lighttube.data.repository.AccountRepository;
import com.example.scame.lighttube.data.repository.AccountRepositoryImp;
import com.example.scame.lighttube.data.repository.CategoryRepository;
import com.example.scame.lighttube.data.repository.CategoryRepositoryImp;
import com.example.scame.lighttube.data.repository.ChannelVideosRepositoryImp;
import com.example.scame.lighttube.data.repository.CommentsRepository;
import com.example.scame.lighttube.data.repository.CommentsRepositoryImp;
import com.example.scame.lighttube.data.repository.ContentDetailsRepositoryImp;
import com.example.scame.lighttube.data.repository.ChannelVideosRepository;
import com.example.scame.lighttube.data.repository.ContentDetailsRepository;
import com.example.scame.lighttube.data.repository.PaginationUtility;
import com.example.scame.lighttube.data.repository.RatingRepository;
import com.example.scame.lighttube.data.repository.RecentVideosRepository;
import com.example.scame.lighttube.data.repository.RecentVideosRepositoryImp;
import com.example.scame.lighttube.data.repository.SearchRepository;
import com.example.scame.lighttube.data.repository.StatisticsRepository;
import com.example.scame.lighttube.data.repository.UserChannelRepository;
import com.example.scame.lighttube.data.repository.HomeVideosRepository;
import com.example.scame.lighttube.data.repository.PaginationUtilityImp;
import com.example.scame.lighttube.data.repository.RatingRepositoryImp;
import com.example.scame.lighttube.data.repository.SearchRepositoryImp;
import com.example.scame.lighttube.data.repository.StatisticsRepositoryImp;
import com.example.scame.lighttube.data.repository.UserChannelRepositoryImp;
import com.example.scame.lighttube.data.repository.HomeVideosRepositoryImp;
import com.example.scame.lighttube.data.rest.ChannelsApi;
import com.example.scame.lighttube.data.rest.CommentsApi;
import com.example.scame.lighttube.data.rest.RatingApi;
import com.example.scame.lighttube.data.rest.RecentVideosApi;
import com.example.scame.lighttube.data.rest.SearchApi;
import com.example.scame.lighttube.data.rest.StatisticsApi;
import com.example.scame.lighttube.data.rest.VideoListApi;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoriesModule {

    @Singleton
    @Provides
    AccountRepository provideAccountRepository(SharedPreferences sharedPrefs, Context context) {
        return new AccountRepositoryImp(sharedPrefs, context);
    }

    @Singleton
    @Provides
    HomeVideosRepository provideHomeRepository(HomeVideosMapper mapper, VideoListApi videoListApi) {
        return new HomeVideosRepositoryImp(mapper, videoListApi);
    }

    @Singleton
    @Provides
    SearchRepository provideSearchRepository(SearchApi searchApi, SearchListMapper searchListMapper,
                                              AutocompleteDeserializer deserializer, Context context,
                                              ContentDetailsRepository detailsDataManager) {
        return new SearchRepositoryImp(searchApi, searchListMapper, deserializer, context, detailsDataManager);
    }

    @Singleton
    @Provides
    CategoryRepository provideCategoryRepository(SharedPreferences sharedPrefs, Context context, SearchApi searchApi,
                                                  CategoryPairsMapper mapper, @Named("category") List<String> categoryKeys,
                                                  SearchRepository searchDataManager, SearchListMapper searchListMapper) {
        return new CategoryRepositoryImp(sharedPrefs, context, searchApi, mapper, categoryKeys,
                                                            searchDataManager, searchListMapper);
    }

    @Singleton
    @Provides
    RecentVideosRepository provideRecentVideosRepository(RecentVideosApi recentVideosApi, RecentVideosMapper mapper,
                                                          PublishingDateParser parser, SharedPreferences sharedPrefs,
                                                          ContentDetailsRepository contentDetailsRepository,
                                                          ChannelsMapper channelsMapper) {
        return new RecentVideosRepositoryImp(recentVideosApi, mapper, parser, sharedPrefs,
                channelsMapper, contentDetailsRepository);
    }

    @Singleton
    @Provides
    ChannelVideosRepository provideChannelVideosRepository(RecentVideosApi recentVideosApi, SearchListMapper searchListMapper,
                                                           ContentDetailsRepository detailsDataManager) {

        return new ChannelVideosRepositoryImp(recentVideosApi, searchListMapper, detailsDataManager);
    }

    @Singleton
    @Provides
    ContentDetailsRepository provideContentRepository(VideoListApi videoListApi, IdsMapper idsMapper,
                                                          DurationsCombiner combiner) {

        return new ContentDetailsRepositoryImp(videoListApi, idsMapper, combiner);
    }

    @Singleton
    @Provides
    RatingRepository provideRatingRepository(RatingApi ratingApi, RatingMapper ratingMapper) {
        return new RatingRepositoryImp(ratingApi, ratingMapper);
    }

    @Singleton
    @Provides
    CommentsRepository provideCommentsRepository(CommentsApi commentsApi, CommentListMapper commentListMapper,
                                                  ReplyListMapper replyListMapper, ThreadResponseMapper threadMapper,
                                                  ThreadPostBuilder commentBuilder, ReplyPostBuilder replyBuilder,
                                                  ReplyResponseMapper replyMapper, ReplyUpdateBuilder replyUpdateBuilder,
                                                  ThreadUpdateBuilder threadUpdateBuilder) {
        return new CommentsRepositoryImp(commentsApi, commentListMapper, replyListMapper, threadMapper, commentBuilder,
                replyBuilder, replyMapper, replyUpdateBuilder, threadUpdateBuilder);
    }

    @Singleton
    @Provides
    UserChannelRepository provideUserChannelRepository(ChannelsApi channelsApi, SharedPreferences sharedPrefs,
                                                        Context context) {
        return new UserChannelRepositoryImp(channelsApi, sharedPrefs, context);
    }

    @Singleton
    @Provides
    StatisticsRepository provideStatisticsRepository(StatisticsApi statisticsApi, VideoStatsMapper statsMapper) {
        return new StatisticsRepositoryImp(statsMapper, statisticsApi);
    }


    @Provides
    PaginationUtility providePaginationUtility(SharedPreferences sharedPreferences, Context context) {
        return new PaginationUtilityImp(sharedPreferences, context);
    }
}
