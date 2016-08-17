package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.SearchUseCase;
import com.example.scame.lighttube.presentation.model.SearchItemModel;

import java.util.List;

public class SearchResultsPresenterImp<V extends ISearchResultsPresenter.SearchResultsView>
        implements ISearchResultsPresenter<V> {

    private static final int FIRST_PAGE = 0;

    private SearchUseCase useCase;

    private V view;

    private int page;

    public SearchResultsPresenterImp(SearchUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void fetchVideos(int page, String query) {
        this.page = page;
        useCase.setQuery(query);
        useCase.setPage(page);
        useCase.execute(new SearchResultsSubscriber());
    }

    @Override
    public void setView(V view) {
        this.view = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    private final class SearchResultsSubscriber extends DefaultSubscriber<List<SearchItemModel>> {

        @Override
        public void onNext(List<SearchItemModel> items) {
            super.onNext(items);

            if (page == FIRST_PAGE) {
                view.initializeAdapter(items);
            } else {
                view.updateAdapter(items);
            }
        }
    }
}
