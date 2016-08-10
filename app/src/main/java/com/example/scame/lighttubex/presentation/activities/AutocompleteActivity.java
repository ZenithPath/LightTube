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
import com.example.scame.lighttubex.data.repository.ISearchDataManager;
import com.example.scame.lighttubex.data.repository.SearchDataManagerImp;
import com.example.scame.lighttubex.presentation.adapters.AutocompleteAdapter;
import com.example.scame.lighttubex.presentation.di.components.ApplicationComponent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AutocompleteActivity extends BaseActivity {

    private AutocompleteAdapter adapter;

    @BindView(R.id.autocomplete_toolbar) Toolbar toolbar;
    @BindView(R.id.autocomplete_lv) ListView autocompleteLv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocomplete_activity);

        ButterKnife.bind(this);

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
    protected void inject(ApplicationComponent appComponent) { }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.videolist_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setQueryHint(getString(R.string.search_hint));
        MenuItemCompat.expandActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.isEmpty()) search(s);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    // TODO: refactor this hardcoded trash

    private void search(String query) {
        ISearchDataManager dataManager = new SearchDataManagerImp();
        dataManager.autocomplete(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(autocompleteEntity -> {
                    adapter.clear();
                    adapter.addAll(autocompleteEntity.getItems());
                    adapter.notifyDataSetChanged();
                });
    }
}
