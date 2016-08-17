package com.example.scame.lighttube.data.di;

import com.example.scame.lighttube.data.interceptors.AccessTokenInterceptor;
import com.example.scame.lighttube.data.interceptors.TokenAuthenticator;
import com.example.scame.lighttube.data.repository.ISearchDataManager;
import com.example.scame.lighttube.data.repository.IAccountDataManager;
import com.example.scame.lighttube.data.repository.IVideoListDataManager;
import com.example.scame.lighttube.data.repository.SearchDataManagerImp;
import com.example.scame.lighttube.data.repository.AccountDataManagerImp;
import com.example.scame.lighttube.data.repository.VideoListDataManagerImp;

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
public class DataModule {

    @Singleton
    @Provides
    OkHttpClient provideOkHttp() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new AccessTokenInterceptor())
                .authenticator(new TokenAuthenticator())
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    IAccountDataManager provideSignInDataManager() {
        return new AccountDataManagerImp();
    }

    @Singleton
    @Provides
    IVideoListDataManager provideVideoListDataManager() {
        return new VideoListDataManagerImp();
    }

    @Singleton
    @Provides
    ISearchDataManager provideSearchDataManager() { return new SearchDataManagerImp(); }
}
