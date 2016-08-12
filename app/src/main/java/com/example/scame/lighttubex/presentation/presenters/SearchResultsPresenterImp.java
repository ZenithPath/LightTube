package com.example.scame.lighttubex.presentation.presenters;


import com.example.scame.lighttubex.data.entities.search.SearchEntity;
import com.example.scame.lighttubex.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttubex.domain.usecases.SearchUseCase;

public class SearchResultsPresenterImp<V extends ISearchResultsPresenter.SearchResultsView>
                                            implements ISearchResultsPresenter<V> {

    private SearchUseCase useCase;

    private V view;

    public SearchResultsPresenterImp(SearchUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void fetchVideos(int page, SearchEntity searchEntity, String query) {
        useCase.setQuery(query);
        //useCase.setPage(page);
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

    private final class SearchResultsSubscriber extends DefaultSubscriber<SearchEntity> {

        @Override
        public void onNext(SearchEntity searchEntity) {
            super.onNext(searchEntity);

            view.initializeAdapter(searchEntity);
        }
    }
}
