package com.example.scame.lighttube.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.PlayVideoActivity;
import com.example.scame.lighttube.presentation.activities.SearchActivity;

public class Navigator {

    public Navigator() {}

    public void navigateToPlayVideo(Context context, String videoId) {
        if (context != null) {
            Intent intentToLaunch = new Intent(context, PlayVideoActivity.class);
            intentToLaunch.putExtra(context.getString(R.string.video_id), videoId);
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
