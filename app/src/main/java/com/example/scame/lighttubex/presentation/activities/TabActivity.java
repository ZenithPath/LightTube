package com.example.scame.lighttubex.presentation.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.scame.lighttubex.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class TabActivity extends AppCompatActivity {

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottom_tabs_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.home_menu_item:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.channels_menu_item:
                        Toast.makeText(getApplicationContext(), "Channels", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.discover_menu_item:
                        Toast.makeText(getApplicationContext(), "Discover", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.account_menu_item:
                        Toast.makeText(getApplicationContext(), "Account", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.home_menu_item:
                        Toast.makeText(getApplicationContext(), "HomeReselected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.channels_menu_item:
                        Toast.makeText(getApplicationContext(), "ChannelsReselected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.discover_menu_item:
                        Toast.makeText(getApplicationContext(), "DiscoverReselected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.account_menu_item:
                        Toast.makeText(getApplicationContext(), "AccountReselected", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        //startActivity(new Intent(this, VideoListActivity.class));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mBottomBar.onSaveInstanceState(outState);
    }
}
