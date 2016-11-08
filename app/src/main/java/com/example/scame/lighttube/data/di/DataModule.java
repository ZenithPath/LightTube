package com.example.scame.lighttube.data.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Pair;

import com.example.scame.lighttube.R;

import java.util.Arrays;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {RepositoriesModule.class, MappersModule.class, NetworkingModule.class})
public class DataModule {

    @Singleton
    @Provides
    @Named("category")
    List<String> provideCategoryKeys(Context context) {
        return Arrays.asList(context.getResources().getStringArray(R.array.category_items));
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Named("repliesKeys")
    @Provides
    Pair<Integer, Integer> provideRepliesKeys() {
        return new Pair<>(R.string.replies_next_page_token, R.string.replies_page_number);
    }

    @Singleton
    @Named("commentsKeys")
    @Provides
    Pair<Integer, Integer> provideCommentsKeys() {
        return new Pair<>(R.string.comments_next_page_token, R.string.comments_page_number);
    }

    @Singleton
    @Named("generalKeys")
    @Provides
    Pair<Integer, Integer> provideGeneralKeys() {
        return new Pair<>(R.string.next_page_token_general, R.string.next_page_general);
    }
}
