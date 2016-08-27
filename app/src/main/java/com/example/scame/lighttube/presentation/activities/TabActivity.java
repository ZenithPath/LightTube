package com.example.scame.lighttube.presentation.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.di.components.ApplicationComponent;
import com.example.scame.lighttube.presentation.di.components.ComponentsManager;
import com.example.scame.lighttube.presentation.di.components.DaggerTabComponent;
import com.example.scame.lighttube.presentation.di.components.GridComponent;
import com.example.scame.lighttube.presentation.di.components.SignInComponent;
import com.example.scame.lighttube.presentation.di.components.VideoListComponent;
import com.example.scame.lighttube.presentation.di.modules.TabModule;
import com.example.scame.lighttube.presentation.fragments.GridFragment;
import com.example.scame.lighttube.presentation.fragments.SignInFragment;
import com.example.scame.lighttube.presentation.fragments.SurpriseMeFragment;
import com.example.scame.lighttube.presentation.fragments.VideoListFragment;
import com.example.scame.lighttube.presentation.presenters.ITabActivityPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabActivity extends BaseActivity implements VideoListFragment.VideoListActivityListener,
                                                    ITabActivityPresenter.ITabActivityView,
                                                    SignInFragment.SignUpListener,
                                                    SurpriseMeFragment.SurpriseMeListener {

    public static final String VIDEO_LIST_FRAG_TAG = "videoListFragment";

    public static final String SIGN_IN_FRAG_TAG = "signInFragment";

    public static final String SURPRISE_ME_FRAG_TAG = "surpriseMeFragment";

    public static final String GRID_FRAG_TAG = "gridFragment";

    private static final int HOME_TAB = 0;
    private static final int CHANNELS_TAB = 1;
    private static final int DISCOVER_TAB_SIGN_IN = 2;
    private static final int DISCOVER_TAB_SIGN_OUT = 1;
    private static final int ACCOUNT_TAB_SIGN_IN = 3;
    private static final int ACCOUNT_TAB_SIGN_OUT = 2;

    @BindView(R.id.bottom_navigation_bar) BottomNavigationBar bottomNavigationBar;

    @Inject
    ITabActivityPresenter<ITabActivityPresenter.ITabActivityView> presenter;

    private MenuItem searchItem;

    private BottomNavigationItem[] bottomBarItems;

    private ComponentsManager componentsManager;

    private VideoListComponent videoListComponent;
    private SignInComponent signInComponent;
    private GridComponent gridComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        componentsManager = new ComponentsManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity);


        if (getSupportFragmentManager().findFragmentByTag(VIDEO_LIST_FRAG_TAG) == null) {
            replaceFragment(R.id.tab_activity_fl, new VideoListFragment(), VIDEO_LIST_FRAG_TAG);
        }

        ButterKnife.bind(this);
        presenter.setView(this);

        presenter.checkLogin();

        bottomBarItems = new BottomNavigationItem[] {
                new BottomNavigationItem(R.drawable.ic_home_black_24dp, getString(R.string.home_item)),
                new BottomNavigationItem(R.drawable.ic_video_library_black_24dp, getString(R.string.channels_item)),
                new BottomNavigationItem(R.drawable.ic_lightbulb_outline_black_24dp, getString(R.string.discover_item)),
                new BottomNavigationItem(R.drawable.ic_account_box_black_24dp, getString(R.string.account_item))
        };
    }

    // called by presenter after checkLogin method in onCreate
    @Override
    public void setBottomBarItems(boolean isSignedIn) {

        if (isSignedIn) {
            initSignInBottomBar();
        } else {
            initSignOutBottomBar();
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

    private void initSignInBottomBar() {
        setBottomNavigationColors(); // workaround for navigationBar colors problem
        configSignInBottomBar();
        bottomNavigationBar.initialise();
    }

    private void initSignOutBottomBar() {
        configSignOutBottomBar();
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
                        Toast.makeText(getApplicationContext(), "Channels", Toast.LENGTH_SHORT).show();
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

            }
        };
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

            }
        };
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
    protected void inject(ApplicationComponent appComponent) {
        DaggerTabComponent.builder()
                .applicationComponent(appComponent)
                .tabModule(new TabModule())
                .build()
                .inject(this);
    }
}
