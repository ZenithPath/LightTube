package com.example.scame.lighttubex.presentation.activities;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.presentation.di.HasComponent;
import com.example.scame.lighttubex.presentation.di.components.ApplicationComponent;
import com.example.scame.lighttubex.presentation.di.components.DaggerSearchComponent;
import com.example.scame.lighttubex.presentation.di.components.SearchComponent;
import com.example.scame.lighttubex.presentation.di.modules.SearchModule;
import com.example.scame.lighttubex.presentation.fragments.AutocompleteFragment;
import com.example.scame.lighttubex.presentation.fragments.SearchResultsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity implements HasComponent<SearchComponent>,
                                                               AutocompleteFragment.AutocompleteFragmentListener,
                                                                SearchResultsFragment.SearchResultsListener {

    public static final String AUTOCOMPLETE_FRAG_TAG = "autocomplete";
    public static final String SEARCH_FRAG_TAG = "searchFragment";

    @BindView(R.id.autocomplete_toolbar) Toolbar toolbar;

    private SearchView searchView;

    private SearchComponent component;

    private Bundle state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        state = savedInstanceState;

        if (getSupportFragmentManager().findFragmentByTag(AUTOCOMPLETE_FRAG_TAG) == null) {
            addFragment(R.id.search_activity_fl, new AutocompleteFragment(), AUTOCOMPLETE_FRAG_TAG);
        }

        ButterKnife.bind(this);

        configureToolbar();
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void inject(ApplicationComponent appComponent) {
        component = DaggerSearchComponent.builder()
                .applicationComponent(appComponent)
                .searchModule(new SearchModule(this))
                .build();


        component.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.videolist_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setQueryHint(getString(R.string.search_hint));

        if (state == null) {
            MenuItemCompat.expandActionView(menuItem);
        }

        searchView.setOnQueryTextListener(buildOnQueryTextListener());
        searchView.setOnSearchClickListener(view -> enableAutocomplete());

        return super.onCreateOptionsMenu(menu);
    }

    public void enableAutocomplete() {

        // if there's a search results fragment in activity manager - remove it,
        // so an autocomplete fragment is on top and visible
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SearchActivity.SEARCH_FRAG_TAG);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    private SearchView.OnQueryTextListener buildOnQueryTextListener() {
        final AutocompleteFragment af = (AutocompleteFragment) getSupportFragmentManager()
                .findFragmentByTag(AUTOCOMPLETE_FRAG_TAG);

        return af.buildOnQueryTextListener();
    }

    @Override
    public SearchComponent getComponent() {
        return component;
    }

    @Override
    public void updateSearchView(String query) {
        searchView.setQuery(query, true);
    }

    @Override
    public void onQueryTextSubmit(String query) {
        SearchResultsFragment fragment = new SearchResultsFragment();

        Bundle args = new Bundle();
        args.putString(getString(R.string.search_query), query);
        fragment.setArguments(args);
        addFragment(R.id.search_activity_fl, fragment, SEARCH_FRAG_TAG);

        hideKeyboard();
    }

    private void addFragment(int layoutId, Fragment fragment, String TAG) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(layoutId, fragment, TAG)
                .commit();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onVideoClick(String id) {
        navigator.navigateToPlayVideo(this, id);
    }
}
