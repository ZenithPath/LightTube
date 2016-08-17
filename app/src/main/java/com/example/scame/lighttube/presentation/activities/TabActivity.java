package com.example.scame.lighttube.presentation.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.di.components.ApplicationComponent;
import com.example.scame.lighttube.presentation.di.components.ComponentsManager;
import com.example.scame.lighttube.presentation.di.components.SearchComponent;
import com.example.scame.lighttube.presentation.di.components.SignInComponent;
import com.example.scame.lighttube.presentation.di.components.VideoListComponent;
import com.example.scame.lighttube.presentation.fragments.SignInFragment;
import com.example.scame.lighttube.presentation.fragments.VideoListFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabActivity extends BaseActivity implements VideoListFragment.VideoListActivityListener {

    public static final String VIDEO_LIST_FRAG_TAG = "videoListFragment";

    public static final String SIGN_IN_FRAG_TAG = "signInFragment";

    private MenuItem searchItem;

    @BindView(R.id.videolist_toolbar) Toolbar toolbar;

    private BottomBar bottomBar;

    private ComponentsManager componentsManager;

    private VideoListComponent videoListComponent;
    private SignInComponent signInComponent;
    private SearchComponent searchComponent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        componentsManager = new ComponentsManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list_activity);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        configureBottomBar(savedInstanceState);
    }


    private void configureBottomBar(Bundle savedInstanceState) {
        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItems(R.menu.bottom_tabs_menu);
        bottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.home_menu_item:
                        if (getSupportFragmentManager().findFragmentByTag(VIDEO_LIST_FRAG_TAG) == null) {
                            replaceFragment(R.id.videolist_activity_fl, new VideoListFragment(), VIDEO_LIST_FRAG_TAG);
                        }

                        break;
                    case R.id.channels_menu_item:
                        Toast.makeText(getApplicationContext(), "Channels", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.discover_menu_item:
                        Toast.makeText(getApplicationContext(), "Discover", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.account_menu_item:
                        if (getSupportFragmentManager().findFragmentByTag(SIGN_IN_FRAG_TAG) == null) {
                            replaceFragment(R.id.videolist_activity_fl, new SignInFragment(), SIGN_IN_FRAG_TAG);
                        }

                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.home_menu_item:
                        VideoListFragment fragment = (VideoListFragment) getSupportFragmentManager()
                                .findFragmentByTag(VIDEO_LIST_FRAG_TAG);
                        if (fragment != null) {
                            fragment.scrollToTop();
                        }
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
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        searchItem.collapseActionView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.videolist_menu, menu);

        searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnSearchClickListener(view -> navigator.navigateToAutocompleteActivity(this));

        return super.onCreateOptionsMenu(menu);
    }

    public VideoListComponent getVideoListComponent() {
        if (videoListComponent == null) {
            videoListComponent = componentsManager.provideVideoListComponent();
        }

        return videoListComponent;
    }

    public SignInComponent getSignInComponent() {
        if (signInComponent == null) {
            signInComponent = componentsManager.provideSignInComponent();
        }

        return signInComponent;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        bottomBar.onSaveInstanceState(outState);
    }

    @Override
    public void onVideoClick(String id) {
        navigator.navigateToPlayVideo(this, id);
    }

    @Override
    protected void inject(ApplicationComponent appComponent) {
        navigator = appComponent.getNavigator();
    }
}
