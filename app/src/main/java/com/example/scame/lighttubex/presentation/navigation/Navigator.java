package com.example.scame.lighttubex.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.example.scame.lighttubex.presentation.activities.VideoListActivity;
import com.example.scame.lighttubex.presentation.di.PerActivity;

import javax.inject.Inject;

@PerActivity
public class Navigator {

    @Inject
    public Navigator() {}

    public void navigateToVideoList(Context context) {
        if (context != null) {
            Intent intentToLaunch = new Intent(context, VideoListActivity.class);
            context.startActivity(intentToLaunch);
        }
    }
}