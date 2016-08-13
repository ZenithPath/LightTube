package com.example.scame.lighttubex.presentation.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.presentation.adapters.AutocompleteAdapter;
import com.example.scame.lighttubex.presentation.di.components.SearchComponent;
import com.example.scame.lighttubex.presentation.presenters.IAutocompletePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.scame.lighttubex.presentation.presenters.IAutocompletePresenter.AutocompleteView;

public class AutocompleteFragment extends BaseFragment implements AutocompleteView {

    @BindView(R.id.autocomplete_lv) ListView autocompleteLv;

    private AutocompleteAdapter adapter;

    private List<String> autocompleteList;

    @Inject
    IAutocompletePresenter<AutocompleteView> presenter;

    private AutocompleteFragmentListener listener;

    public interface AutocompleteFragmentListener {

        void updateSearchView(String query);

        void onQueryTextSubmit(String query);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof AutocompleteFragmentListener) {
            listener = (AutocompleteFragmentListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.autocomplete_fragment, container, false);

        ButterKnife.bind(this, fragmentView);
        getComponent(SearchComponent.class).inject(this);

        presenter.setView(this);

        configureListView();

        if (savedInstanceState != null) {
            updateAutocompleteList(savedInstanceState.getStringArrayList(getString(R.string.autocomplete_list)));
        }

        return fragmentView;
    }


    private void configureListView() {
        adapter = new AutocompleteAdapter(getContext(), R.layout.autocomplete_item, new ArrayList<>());
        autocompleteLv.setAdapter(adapter);
        autocompleteLv.setOnItemClickListener((adapterView, view, position, id) ->
                listener.updateSearchView(adapterView.getItemAtPosition(position).toString())
        );
    }

    @Override
    public void updateAutocompleteList(List<String> strings) {
        autocompleteList = strings;

        adapter.clear();
        adapter.addAll(strings);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList(getString(R.string.autocomplete_list),
                new ArrayList<>(autocompleteList));
    }

    public SearchView.OnQueryTextListener buildOnQueryTextListener() {

        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listener.onQueryTextSubmit(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (!newText.isEmpty()) {
                    enableAutocomplete();
                    presenter.updateAutocompleteList(newText);
                }

                return true;
            }
        };
    }

    private void enableAutocomplete() {

        // if there's a search results fragment in backstack - remove it,
        // so autocomplete fragment is on top and visible
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            getFragmentManager().popBackStack();
        }
    }
}
