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
import com.example.scame.lighttubex.presentation.adapters.EndlessRecyclerViewScrollingListener;
import com.example.scame.lighttubex.presentation.adapters.SearchResultsAdapter;
import com.example.scame.lighttubex.presentation.di.components.SearchComponent;
import com.example.scame.lighttubex.presentation.model.SearchItemModel;
import com.example.scame.lighttubex.presentation.presenters.ISearchResultsPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.scame.lighttubex.presentation.presenters.ISearchResultsPresenter.SearchResultsView;

public class SearchResultsFragment extends BaseFragment implements SearchResultsView {

    @BindView(R.id.search_results_rv) RecyclerView recyclerView;

    @Inject ISearchResultsPresenter<SearchResultsView> presenter;

    private SearchResultsAdapter adapter;

    private List<SearchItemModel> searchItems;

    private int currentPage;

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

        if (savedInstanceState != null) {
            currentPage = savedInstanceState.getInt(getString(R.string.page_number), 0);
            initializeAdapter(savedInstanceState.getParcelableArrayList(getString(R.string.search_items_list)));
        } else {
            presenter.fetchVideos(currentPage, query);
        }

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(getString(R.string.search_items_list), new ArrayList<>(searchItems));
        outState.putInt(getString(R.string.page_number), currentPage);
    }

    @Override
    public void initializeAdapter(List<SearchItemModel> items) {
        searchItems = items;
        adapter = new SearchResultsAdapter(items, getContext());
        adapter.setupOnItemClickListener((itemView, position) ->
                listener.onVideoClick(searchItems.get(position).getId()));

        LinearLayoutManager layoutManager = buildLayoutManager();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(buildScrollingListener(layoutManager));
    }

    @Override
    public void updateAdapter(List<SearchItemModel> items) {
        searchItems.addAll(items);
        adapter.notifyItemRangeInserted(adapter.getItemCount(), items.size());
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

    private EndlessRecyclerViewScrollingListener buildScrollingListener(LinearLayoutManager manager) {
        EndlessRecyclerViewScrollingListener listener = new EndlessRecyclerViewScrollingListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                currentPage = page;
                presenter.fetchVideos(page, query);
            }
        };

        listener.setCurrentPage(currentPage);

        return listener;
    }
}
