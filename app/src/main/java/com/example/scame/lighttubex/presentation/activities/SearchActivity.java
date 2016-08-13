package com.example.scame.lighttubex.presentation.activities;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
            addAutocompleteFragment();
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
    public void onBackPressed() {

        if (isSearchFragmentActive()) {
            getSupportFragmentManager().popBackStack();
        }

        super.onBackPressed();
    }

    public boolean isSearchFragmentActive() {
        return getSupportFragmentManager().getBackStackEntryCount() == 1;
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
        searchView.setOnSearchClickListener(view -> addAutocompleteFragment());

        return super.onCreateOptionsMenu(menu);
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

        hideKeyboard();


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.search_activity_fl, fragment, SEARCH_FRAG_TAG)
                .addToBackStack(null)
                .commit();
    }

    private void addAutocompleteFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.search_activity_fl, new AutocompleteFragment(), AUTOCOMPLETE_FRAG_TAG)
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
