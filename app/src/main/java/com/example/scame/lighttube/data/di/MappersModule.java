package com.example.scame.lighttube.data.di;

import com.example.scame.lighttube.data.mappers.CategoryPairsMapper;
import com.example.scame.lighttube.data.mappers.ChannelsMapper;
import com.example.scame.lighttube.data.mappers.DurationsCombiner;
import com.example.scame.lighttube.data.mappers.IdsMapper;
import com.example.scame.lighttube.data.mappers.AutocompleteDeserializer;
import com.example.scame.lighttube.data.mappers.PublishingDateParser;
import com.example.scame.lighttube.data.mappers.RecentVideosMapper;
import com.example.scame.lighttube.data.mappers.SearchListMapper;
import com.example.scame.lighttube.data.mappers.SubscriptionsIdsMapper;
import com.example.scame.lighttube.data.mappers.VideoListMapper;

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
    VideoListMapper provideVideoListMapper() {
        return new VideoListMapper();
    }
}