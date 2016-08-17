package com.example.scame.lighttubex.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.scame.lighttubex.presentation.LightTubeApp;
import com.example.scame.lighttubex.presentation.di.components.ApplicationComponent;
import com.example.scame.lighttubex.presentation.navigation.Navigator;

public abstract class BaseActivity extends AppCompatActivity {

    protected Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        inject(getAppComponent()); // to avoid NPE from fragments after config changes
        super.onCreate(savedInstanceState);

    }

    protected void replaceFragment(int containerViewId, Fragment fragment, String TAG) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(containerViewId, fragment, TAG);
        ft.commit();

    }

    protected ApplicationComponent getAppComponent() {
        return ((LightTubeApp) getApplication()).getAppComponent();
    }

    protected abstract void inject(ApplicationComponent appComponent);
}
