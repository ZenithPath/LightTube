package com.example.scame.lighttubex.presentation.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.data.entities.search.SearchEntity;
import com.example.scame.lighttubex.data.entities.search.SearchItem;
import com.example.scame.lighttubex.presentation.adapters.SearchResultsAdapter;
import com.example.scame.lighttubex.presentation.di.components.SearchComponent;
import com.example.scame.lighttubex.presentation.presenters.ISearchResultsPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.scame.lighttubex.presentation.presenters.ISearchResultsPresenter.SearchResultsView;

public class SearchResultsFragment extends BaseFragment implements SearchResultsView {

    @BindView(R.id.search_results_rv) RecyclerView recyclerView;

    @Inject ISearchResultsPresenter<SearchResultsView> presenter;

    private SearchResultsAdapter adapter;

    private List<SearchItem> searchItems;

    private SearchResultsListener listener;

    private String query;

    public interface SearchResultsListener {

        void onVideoClick(String id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SearchResultsListener) {
            listener = (SearchResultsListener) context;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.search_results_fragment, container, false);

        ButterKnife.bind(this, fragmentView);
        getComponent(SearchComponent.class).inject(this);
        query = getQuery();

        presenter.setView(this);
        presenter.fetchVideos(0, null, query);

        return fragmentView;
    }



    @Override
    public void initializeAdapter(SearchEntity searchEntity) {
        searchItems = searchEntity.getItems();
        adapter = new SearchResultsAdapter(searchItems, getContext());
        adapter.setupOnItemClickListener((itemView, position) ->
                listener.onVideoClick(searchItems.get(position).getId().getVideoId()));

        LinearLayoutManager layoutManager = buildLayoutManager();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void updateAdapter(SearchEntity searchEntity) {
        // TODO: implement endless scrolling
    }


    private String getQuery() {
        Bundle args = getArguments();
        return args.getString(getString(R.string.search_query), "");
    }

    private LinearLayoutManager buildLayoutManager() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        } else {
            return new LinearLayoutManager(getContext());
        }
    }
}
