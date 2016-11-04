package com.example.scame.lighttube.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.PlayerActivity;
import com.example.scame.lighttube.presentation.activities.SearchActivity;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.model.VideoModel;

import javax.inject.Inject;

@PerActivity
public class Navigator {

    @Inject
    public Navigator() {}

    public void navigateToPlayVideo(Context context, VideoModel videoModel) {
        if (context != null) {
            Intent intentToLaunch = new Intent(context, PlayerActivity.class);
            intentToLaunch.putExtra(context.getString(R.string.video_model_key), videoModel);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToAutocompleteActivity(Context context) {
        if (context != null) {
            Intent intentToLaunch = new Intent(context, SearchActivity.class);
            context.startActivity(intentToLaunch);
        }
    }
}
