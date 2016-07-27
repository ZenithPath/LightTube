package com.example.scame.lighttubex.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.presentation.di.components.ApplicationComponent;

public class VideoListActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list_activity);
    }

    @Override
    protected void inject(ApplicationComponent appComponent) {

    }
}
