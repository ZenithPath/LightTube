package com.example.scame.lighttube.data.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.scame.lighttube.R;

import java.util.Arrays;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DataManagersModule.class, MappersModule.class, NetworkingModule.class})
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
}
