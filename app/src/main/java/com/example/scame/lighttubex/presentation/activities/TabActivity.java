package com.example.scame.lighttubex.presentation.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.presentation.di.components.ApplicationComponent;
import com.example.scame.lighttubex.presentation.di.components.ComponentsManager;
import com.example.scame.lighttubex.presentation.di.components.SearchComponent;
import com.example.scame.lighttubex.presentation.di.components.SignInComponent;
import com.example.scame.lighttubex.presentation.di.components.VideoListComponent;
import com.example.scame.lighttubex.presentation.fragments.VideoListFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabActivity extends BaseActivity implements VideoListFragment.VideoListActivityListener {

    public static final String VIDEO_LIST_FRAG_TAG = "videoListFragment";

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

        replaceFragment(R.id.videolist_activity_fl, new VideoListFragment(), VIDEO_LIST_FRAG_TAG);

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
                        detachOldFragment();

                        VideoListFragment videoListFragment = (VideoListFragment) getSupportFragmentManager()
                                .findFragmentByTag(VIDEO_LIST_FRAG_TAG);
                        if (videoListFragment == null) {
                            addFragment(VIDEO_LIST_FRAG_TAG, new VideoListFragment());
                        } else {
                            attachNewFragment(videoListFragment);
                        }

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

    public VideoListComponent getVideoListComponent() {
        if (videoListComponent == null) {
            videoListComponent = componentsManager.provideVideoListComponent();
            return videoListComponent;
        } else {
            return videoListComponent;
        }
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

    private void detachOldFragment() {
        Fragment oldFragment = getVisibleFragment();
        if (oldFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .detach(oldFragment)
                    .commit();
        }
    }

    private void attachNewFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .attach(fragment)
                .commit();
    }

    private void addFragment(String TAG, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.videolist_activity_fl, fragment, TAG)
                .commit();
    }

    private Fragment getVisibleFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }
}
