package com.example.scame.lighttube.presentation.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.ConnectivityReceiver;
import com.example.scame.lighttube.presentation.di.components.ApplicationComponent;
import com.example.scame.lighttube.presentation.di.components.ChannelVideosComponent;
import com.example.scame.lighttube.presentation.di.components.ComponentsManager;
import com.example.scame.lighttube.presentation.di.components.DaggerTabComponent;
import com.example.scame.lighttube.presentation.di.components.GridComponent;
import com.example.scame.lighttube.presentation.di.components.RecentVideosComponent;
import com.example.scame.lighttube.presentation.di.components.SignInComponent;
import com.example.scame.lighttube.presentation.di.components.VideoListComponent;
import com.example.scame.lighttube.presentation.di.modules.TabModule;
import com.example.scame.lighttube.presentation.fragments.ChannelVideosFragment;
import com.example.scame.lighttube.presentation.fragments.GridFragment;
import com.example.scame.lighttube.presentation.fragments.NoInternetFragment;
import com.example.scame.lighttube.presentation.fragments.RecentVideosFragment;
import com.example.scame.lighttube.presentation.fragments.SignInFragment;
import com.example.scame.lighttube.presentation.fragments.SurpriseMeFragment;
import com.example.scame.lighttube.presentation.fragments.VideoListFragment;
import com.example.scame.lighttube.presentation.presenters.ITabActivityPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.State;

public class TabActivity extends BaseActivity implements VideoListFragment.VideoListActivityListener,
                                                    ITabActivityPresenter.ITabActivityView,
                                                    SignInFragment.SignUpListener,
                                                    SurpriseMeFragment.SurpriseMeListener,
                                                    RecentVideosFragment.RecentVideosListener,
                                                    ChannelVideosFragment.ChannelVideosListener,
                                                    GridFragment.GridFragmentListener,
                                                    NoInternetFragment.InternetConnectionListener {

    public static final String VIDEO_LIST_FRAG_TAG = "videoListFragment";
    public static final String SIGN_IN_FRAG_TAG = "signInFragment";
    public static final String SURPRISE_ME_FRAG_TAG = "surpriseMeFragment";
    public static final String GRID_FRAG_TAG = "gridFragment";
    public static final String RECENT_FRAG_TAG = "recentFragment";
    public static final String CHANNELS_FRAG_TAG = "chanenelsTabFragm";
    public static final String NO_INTERNET_FRAG_TAG = "noInternetTag";

    private static final int DEFAULT_SELECTED_POSITION = -1;
    private static int PREVIOUSLY_SELECTED_TAB = DEFAULT_SELECTED_POSITION;

    private static final int HOME_TAB = 0;
    private static final int CHANNELS_TAB = 1;
    private static final int DISCOVER_TAB_SIGN_IN = 2;
    private static final int DISCOVER_TAB_SIGN_OUT = 1;
    private static final int ACCOUNT_TAB_SIGN_IN = 3;
    private static final int ACCOUNT_TAB_SIGN_OUT = 2;

    @BindView(R.id.bottom_navigation_bar) BottomNavigationBar bottomNavigationBar;

    @Inject
    ITabActivityPresenter<ITabActivityPresenter.ITabActivityView> presenter;

    // shows how activity was initialized before onDestroy method call
    @State boolean initializedWithoutInternet;

    private MenuItem searchItem;

    private BottomNavigationItem[] bottomBarItems;

    private ComponentsManager componentsManager;

    private VideoListComponent videoListComponent;
    private SignInComponent signInComponent;
    private GridComponent gridComponent;
    private RecentVideosComponent recentVideosComponent;
    private ChannelVideosComponent channelVideosComponent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        componentsManager = new ComponentsManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity);

        ButterKnife.bind(this);
        presenter.setView(this);


        if (!ConnectivityReceiver.isConnected() && savedInstanceState == null) {
            initializeWithoutInternet();
            initializedWithoutInternet = true;
        } else if (savedInstanceState != null && initializedWithoutInternet) {
            initializeWithoutInternet();
        } else {
            initializeWithInternet();
        }
    }

    private void initializeWithInternet() {

        // happens when an activity was recreated with NoInternetFragment & connection shows up
        // or activity simply wasn't recreated
        if (PREVIOUSLY_SELECTED_TAB == DEFAULT_SELECTED_POSITION) {
            replaceFragment(R.id.tab_activity_fl, new VideoListFragment(), VIDEO_LIST_FRAG_TAG);
        }

        presenter.checkLogin();

        bottomBarItems = new BottomNavigationItem[] {
                new BottomNavigationItem(R.drawable.ic_home_black_24dp, getString(R.string.home_item)),
                new BottomNavigationItem(R.drawable.ic_video_library_black_24dp, getString(R.string.channels_item)),
                new BottomNavigationItem(R.drawable.ic_lightbulb_outline_black_24dp, getString(R.string.discover_item)),
                new BottomNavigationItem(R.drawable.ic_account_box_black_24dp, getString(R.string.account_item))
        };
    }

    private void initializeWithoutInternet() {
        replaceFragment(R.id.tab_activity_fl, new NoInternetFragment(), NO_INTERNET_FRAG_TAG);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        PREVIOUSLY_SELECTED_TAB = bottomNavigationBar.getCurrentSelectedPosition();
    }

    // called by presenter after checkLogin method in onCreate
    @Override
    public void setBottomBarItems(boolean isSignedIn) {

        if (isSignedIn) {
            initSignInBottomBar(PREVIOUSLY_SELECTED_TAB);
        } else {
            initSignOutBottomBar(PREVIOUSLY_SELECTED_TAB);
        }
    }

    // called by SignInFragment after user signed in
    @Override
    public void signedIn() {
        configSignInBottomBar();
        bottomNavigationBar.setFirstSelectedPosition(ACCOUNT_TAB_SIGN_IN).initialise();
    }

    // called by SignInFragment after user signed out
    @Override
    public void signedOut() {
        configSignOutBottomBar();
        bottomNavigationBar.setFirstSelectedPosition(ACCOUNT_TAB_SIGN_OUT).initialise();
    }

    private void initSignInBottomBar(int tabToRestore) {
        setBottomNavigationColors(); // workaround for navigationBar colors problem
        configSignInBottomBar();

        if (tabToRestore != -1) {
            bottomNavigationBar.setFirstSelectedPosition(tabToRestore);
        }
        bottomNavigationBar.initialise();
    }

    private void initSignOutBottomBar(int tabToRestore) {
        configSignOutBottomBar();

        if (tabToRestore != -1) {
            bottomNavigationBar.setFirstSelectedPosition(tabToRestore);
        }
        bottomNavigationBar.initialise();
    }

    private void configSignInBottomBar() {
        bottomNavigationBar.clearAll();
        bottomNavigationBar.setTabSelectedListener(signInListener());
        addSignInItems();
    }

    private void configSignOutBottomBar() {
        bottomNavigationBar.clearAll();
        bottomNavigationBar.setTabSelectedListener(signOutListener());
        addSignOutItems();
    }

    private void setBottomNavigationColors() {
        bottomNavigationBar.setBarBackgroundColor(R.color.colorAccent);
        bottomNavigationBar.setActiveColor(R.color.colorPrimary);
        bottomNavigationBar.setInActiveColor(R.color.colorPrimaryDark);
    }

    private void addSignInItems() {
        for (BottomNavigationItem item : bottomBarItems) {
            bottomNavigationBar.addItem(item);
        }
    }

    private void addSignOutItems() {
        bottomNavigationBar
                .addItem(bottomBarItems[HOME_TAB])
                .addItem(bottomBarItems[DISCOVER_TAB_SIGN_IN])
                .addItem(bottomBarItems[ACCOUNT_TAB_SIGN_IN]);
    }

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

    public GridComponent getGridComponent() {
        if (gridComponent == null) {
            gridComponent = componentsManager.provideGridComponent();
        }

        return gridComponent;
    }

    public RecentVideosComponent getRecentVideosComponent() {
        if (recentVideosComponent == null) {
            recentVideosComponent = componentsManager.provideRecentVideosComponent();
        }

        return recentVideosComponent;
    }

    public ChannelVideosComponent getChannelVideosComponent() {
        if (channelVideosComponent == null) {
            channelVideosComponent = componentsManager.provideChannelsComponent();
        }

        return channelVideosComponent;
    }

    @Override
    public void retry() {
        if (ConnectivityReceiver.isConnected()) {
            initializedWithoutInternet = false;
            initializeWithInternet();
        }
    }

    @Override
    public void onVideoClick(String id) {
        navigator.navigateToPlayVideo(this, id);
    }

    @Override
    public void onCategoryItemClick(String category, String duration) {
        GridFragment gridFragment = new GridFragment();
        Bundle args = new Bundle();

        args.putString(getString(R.string.duration_key), duration);
        args.putString(getString(R.string.category_key), category);
        gridFragment.setArguments(args);

        replaceFragment(R.id.tab_activity_fl, gridFragment, GRID_FRAG_TAG);
    }

    @Override
    public void onChannelClick(String channelId) {
        ChannelVideosFragment channelVideosFragment = new ChannelVideosFragment();

        Bundle args = new Bundle();
        args.putString(ChannelVideosFragment.class.getCanonicalName(), channelId);
        channelVideosFragment.setArguments(args);

        replaceFragment(R.id.tab_activity_fl, channelVideosFragment, CHANNELS_FRAG_TAG);
    }

    @Override
    protected void inject(ApplicationComponent appComponent) {
        DaggerTabComponent.builder()
                .applicationComponent(appComponent)
                .tabModule(new TabModule())
                .build()
                .inject(this);
    }

    private BottomNavigationBar.OnTabSelectedListener signOutListener() {
        return new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case HOME_TAB:
                        if (getSupportFragmentManager().findFragmentByTag(VIDEO_LIST_FRAG_TAG) == null) {
                            replaceFragment(R.id.tab_activity_fl, new VideoListFragment(), VIDEO_LIST_FRAG_TAG);
                        }

                        break;
                    case DISCOVER_TAB_SIGN_OUT:
                        if (getSupportFragmentManager().findFragmentByTag(SURPRISE_ME_FRAG_TAG) == null) {
                            replaceFragment(R.id.tab_activity_fl, new SurpriseMeFragment(), SURPRISE_ME_FRAG_TAG);
                        }
                        break;

                    case ACCOUNT_TAB_SIGN_OUT:
                        if (getSupportFragmentManager().findFragmentByTag(SIGN_IN_FRAG_TAG) == null) {
                            replaceFragment(R.id.tab_activity_fl, new SignInFragment(), SIGN_IN_FRAG_TAG);
                        }

                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                switch (position) {
                    case HOME_TAB:
                        VideoListFragment videoListFragment = (VideoListFragment) getSupportFragmentManager()
                                .findFragmentByTag(VIDEO_LIST_FRAG_TAG);

                        if (videoListFragment != null) {
                            videoListFragment.scrollToTop();
                        }

                        break;
                    case DISCOVER_TAB_SIGN_OUT:
                        GridFragment gridFragment = (GridFragment) getSupportFragmentManager()
                                .findFragmentByTag(GRID_FRAG_TAG);

                        if (gridFragment != null) {
                            gridFragment.scrollToTop();
                        }

                        break;
                    case ACCOUNT_TAB_SIGN_OUT:
                        break;
                }
            }
        };
    }

    private BottomNavigationBar.OnTabSelectedListener signInListener() {
        return new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case HOME_TAB:
                        if (getSupportFragmentManager().findFragmentByTag(VIDEO_LIST_FRAG_TAG) == null) {
                            replaceFragment(R.id.tab_activity_fl, new VideoListFragment(), VIDEO_LIST_FRAG_TAG);
                        }

                        break;
                    case CHANNELS_TAB:
                        if (getSupportFragmentManager().findFragmentByTag(RECENT_FRAG_TAG) == null) {
                            replaceFragment(R.id.tab_activity_fl, new RecentVideosFragment(), RECENT_FRAG_TAG);
                        }

                        break;
                    case DISCOVER_TAB_SIGN_IN:
                        if (getSupportFragmentManager().findFragmentByTag(SURPRISE_ME_FRAG_TAG) == null) {
                            replaceFragment(R.id.tab_activity_fl, new SurpriseMeFragment(), SURPRISE_ME_FRAG_TAG);
                        }

                        break;
                    case ACCOUNT_TAB_SIGN_IN:
                        if (getSupportFragmentManager().findFragmentByTag(SIGN_IN_FRAG_TAG) == null) {
                            replaceFragment(R.id.tab_activity_fl, new SignInFragment(), SIGN_IN_FRAG_TAG);
                        }

                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                switch (position) {
                    case HOME_TAB:
                        VideoListFragment videoListFragment = (VideoListFragment) getSupportFragmentManager()
                                .findFragmentByTag(VIDEO_LIST_FRAG_TAG);

                        if (videoListFragment != null) {
                            videoListFragment.scrollToTop();
                        }

                        break;
                    case CHANNELS_TAB:
                        RecentVideosFragment recentVideosFragment = (RecentVideosFragment) getSupportFragmentManager()
                                .findFragmentByTag(RECENT_FRAG_TAG);

                        if (recentVideosFragment != null) {
                            recentVideosFragment.scrollToTop();
                        }

                        break;
                    case DISCOVER_TAB_SIGN_IN:
                        GridFragment gridFragment = (GridFragment) getSupportFragmentManager()
                                .findFragmentByTag(GRID_FRAG_TAG);

                        if (gridFragment != null) {
                            gridFragment.scrollToTop();
                        }

                        break;
                    case ACCOUNT_TAB_SIGN_IN:
                        break;
                }
            }
        };
    }
}
