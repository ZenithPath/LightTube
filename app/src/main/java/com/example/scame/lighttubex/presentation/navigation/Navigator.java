package com.example.scame.lighttubex.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.presentation.activities.PlayVideoActivity;
import com.example.scame.lighttubex.presentation.activities.SignInActivity;
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

    public void navigateToSignIn(Context context){
        if (context != null) {
            Intent intentToLaunch = new Intent(context, SignInActivity.class);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToPlayVideo(Context context, String videoId) {
        if (context != null) {
            Intent intentToLaunch = new Intent(context, PlayVideoActivity.class);
            intentToLaunch.putExtra(context.getString(R.string.video_id), videoId);
            context.startActivity(intentToLaunch);
        }
    }
}
