package com.example.scame.lighttubex.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.data.enteties.TokenEntity;

public class SharedPrefsManagerImp implements SharedPrefsManager {

    private SharedPreferences sp;

    private String accessTokenKey;
    private String refreshTokenKey;

    public SharedPrefsManagerImp(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        accessTokenKey = context.getString(R.string.access_token);
        refreshTokenKey = context.getString(R.string.refresh_token);
    }

    @Override
    public void saveToken(TokenEntity tokenEntity) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(accessTokenKey, tokenEntity.getAccessToken());
        editor.putString(refreshTokenKey, tokenEntity.getRefreshToken());
        editor.apply();
    }
}
