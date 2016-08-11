package com.example.scame.lighttubex.presentation.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.presentation.adapters.AutocompleteAdapter;
import com.example.scame.lighttubex.presentation.di.components.ApplicationComponent;
import com.example.scame.lighttubex.presentation.di.components.DaggerAutocompleteComponent;
import com.example.scame.lighttubex.presentation.di.modules.AutocompleteModule;
import com.example.scame.lighttubex.presentation.presenters.IAutocompletePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.scame.lighttubex.presentation.presenters.IAutocompletePresenter.AutocompleteView;

public class AutocompleteActivity extends BaseActivity implements AutocompleteView {

    private AutocompleteAdapter adapter;

    @BindView(R.id.autocomplete_toolbar) Toolbar toolbar;
    @BindView(R.id.autocomplete_lv) ListView autocompleteLv;

    @Inject
    IAutocompletePresenter<AutocompleteView> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocomplete_activity);

        ButterKnife.bind(this);
        presenter.setView(this);

        configureToolbar();
        configureListView();
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void configureListView() {
        adapter = new AutocompleteAdapter(this, R.layout.autocomplete_item, new ArrayList<>());
        autocompleteLv.setAdapter(adapter);
    }

    @Override
    protected void inject(ApplicationComponent appComponent) {
        DaggerAutocompleteComponent.builder()
                .applicationComponent(appComponent)
                .autocompleteModule(new AutocompleteModule(this))
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.videolist_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setQueryHint(getString(R.string.search_hint));
        MenuItemCompat.expandActionView(menuItem);

        searchView.setOnQueryTextListener(buildOnQueryTextListener());

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void updateAutocompleteList(List<String> strings) {
        adapter.clear();
        adapter.addAll(strings);
        adapter.notifyDataSetChanged();
    }

    private SearchView.OnQueryTextListener buildOnQueryTextListener() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    presenter.updateAutocompleteList(newText);
                }

                return true;
            }
        };
    }
}
