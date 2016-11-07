package com.example.scame.lighttube.data.di;

import com.example.scame.lighttube.data.mappers.AutocompleteDeserializer;
import com.example.scame.lighttube.data.mappers.CategoryPairsMapper;
import com.example.scame.lighttube.data.mappers.ChannelsMapper;
import com.example.scame.lighttube.data.mappers.CommentListMapper;
import com.example.scame.lighttube.data.mappers.DurationsCombiner;
import com.example.scame.lighttube.data.mappers.HomeVideosMapper;
import com.example.scame.lighttube.data.mappers.IdsMapper;
import com.example.scame.lighttube.data.mappers.PublishingDateParser;
import com.example.scame.lighttube.data.mappers.RatingMapper;
import com.example.scame.lighttube.data.mappers.RecentVideosMapper;
import com.example.scame.lighttube.data.mappers.ReplyListMapper;
import com.example.scame.lighttube.data.mappers.ReplyPostBuilder;
import com.example.scame.lighttube.data.mappers.ReplyResponseMapper;
import com.example.scame.lighttube.data.mappers.ReplyUpdateBuilder;
import com.example.scame.lighttube.data.mappers.SearchListMapper;
import com.example.scame.lighttube.data.mappers.SubscriptionsIdsMapper;
import com.example.scame.lighttube.data.mappers.ThreadPostBuilder;
import com.example.scame.lighttube.data.mappers.ThreadResponseMapper;
import com.example.scame.lighttube.data.mappers.ThreadUpdateBuilder;
import com.example.scame.lighttube.data.mappers.VideoStatsMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MappersModule {

    @Singleton @Provides
    CategoryPairsMapper provideCategoryPairsMapper() {
        return new CategoryPairsMapper();
    }

    @Singleton @Provides
    ChannelsMapper provideChannelsMapper() {
        return new ChannelsMapper();
    }

    @Singleton @Provides
    DurationsCombiner provideDurationsCombiner() {
        return new DurationsCombiner();
    }

    @Singleton @Provides
    IdsMapper provideIdsMapper() {
        return new IdsMapper();
    }

    @Singleton @Provides
    AutocompleteDeserializer provideAutocompleteDeserializer() {
        return new AutocompleteDeserializer();
    }

    @Singleton @Provides
    PublishingDateParser providePublishingDateParser() {
        return new PublishingDateParser();
    }

    @Singleton @Provides
    RecentVideosMapper provideRecentVideosMapper(SearchListMapper searchListMapper) {
        return new RecentVideosMapper(searchListMapper);
    }

    @Singleton @Provides
    SearchListMapper provideSearchListMapper() {
        return new SearchListMapper();
    }

    @Singleton @Provides
    SubscriptionsIdsMapper provideSubscriptionsIdsMapper() {
        return new SubscriptionsIdsMapper();
    }

    @Singleton @Provides
    HomeVideosMapper provideHomeVideosMapper() {
        return new HomeVideosMapper();
    }

    @Singleton @Provides
    RatingMapper provideRatingMapper() {
        return new RatingMapper();
    }

    @Singleton @Provides
    CommentListMapper provideCommentListMapper() {
        return new CommentListMapper();
    }

    @Singleton @Provides
    ReplyListMapper provideReplyListMapper() {
        return new ReplyListMapper();
    }

    @Singleton @Provides
    ThreadResponseMapper provideThreadRequestMapper() {
        return new ThreadResponseMapper();
    }

    @Singleton @Provides
    ThreadPostBuilder provideThreadCommentBuilder() {
        return new ThreadPostBuilder();
    }

    @Singleton @Provides
    ReplyPostBuilder provideReplyRequestBuilder() {
        return new ReplyPostBuilder();
    }

    @Singleton @Provides
    ReplyResponseMapper provideReplyRequestMapper() {
        return new ReplyResponseMapper();
    }

    @Singleton @Provides
    ReplyUpdateBuilder provideReplyUpdateBuilder() {
        return new ReplyUpdateBuilder();
    }

    @Singleton @Provides
    ThreadUpdateBuilder provideThreadUpdateBuilder() {
        return new ThreadUpdateBuilder();
    }

    @Singleton @Provides
    VideoStatsMapper provideVideoStatsMapper() {
        return new VideoStatsMapper();
    }
}