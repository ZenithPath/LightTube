package com.example.scame.lighttube.data.di;

import com.example.scame.lighttube.data.interceptors.AccessTokenInterceptor;
import com.example.scame.lighttube.data.interceptors.TokenAuthenticator;
import com.example.scame.lighttube.data.repository.IAccountDataManager;
import com.example.scame.lighttube.data.rest.ChannelsApi;
import com.example.scame.lighttube.data.rest.CommentsApi;
import com.example.scame.lighttube.data.rest.RatingApi;
import com.example.scame.lighttube.data.rest.RecentVideosApi;
import com.example.scame.lighttube.data.rest.SearchApi;
import com.example.scame.lighttube.data.rest.StatisticsApi;
import com.example.scame.lighttube.data.rest.TokenApi;
import com.example.scame.lighttube.data.rest.VideoListApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkingModule {

    private static final String BASE_URL = "https://www.googleapis.com/";

    private static final int READ_TIMEOUT = 10;

    @Singleton
    @Provides
    OkHttpClient provideOkHttp(AccessTokenInterceptor tokenInterceptor,
                               TokenAuthenticator tokenAuthenticator,
                               HttpLoggingInterceptor loggingInterceptor) {

        return new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(tokenInterceptor)
                .authenticator(tokenAuthenticator)
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    RecentVideosApi provideRecentVideosApi(Retrofit retrofit) {
        return retrofit.create(RecentVideosApi.class);
    }

    @Singleton
    @Provides
    SearchApi provideSearchApi(Retrofit retrofit) {
        return retrofit.create(SearchApi.class);
    }

    @Singleton
    @Provides
    TokenApi provideTokenApi(Retrofit retrofit) {
        return retrofit.create(TokenApi.class);
    }

    @Singleton
    @Provides
    VideoListApi provideVideoListApi(Retrofit retrofit) {
        return retrofit.create(VideoListApi.class);
    }

    @Singleton
    @Provides
    RatingApi provideRatingsApi(Retrofit retrofit) {
        return retrofit.create(RatingApi.class);
    }

    @Singleton
    @Provides
    CommentsApi provideCommentsApi(Retrofit retrofit) {
        return retrofit.create(CommentsApi.class);
    }

    @Singleton
    @Provides
    ChannelsApi provideChannelsApi(Retrofit retrofit) {
        return retrofit.create(ChannelsApi.class);
    }

    @Singleton
    @Provides
    StatisticsApi provideStatisticsApi(Retrofit retrofit) {
        return retrofit.create(StatisticsApi.class);
    }

    @Singleton
    @Provides
    AccessTokenInterceptor provideAccessTokenInterceptor(IAccountDataManager accountDataManager) {
        return new AccessTokenInterceptor(accountDataManager);
    }

    @Singleton
    @Provides
    TokenAuthenticator provideTokenAuthenticator(IAccountDataManager accountDataManager) {
        return new TokenAuthenticator(accountDataManager);
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }
}
