package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.SearchUseCase;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;


public class SearchResultsPresenterImp<V extends SearchResultsPresenter.SearchResultsView>
                                                    implements SearchResultsPresenter<V> {
    private static final int FIRST_PAGE = 0;

    private SearchUseCase searchUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private V view;

    public SearchResultsPresenterImp(SearchUseCase searchUseCase, SubscriptionsHandler subscriptionsHandler) {
        this.searchUseCase = searchUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void fetchVideos(int page, String query) {
        searchUseCase.setQuery(query);
        searchUseCase.setPage(page);
        searchUseCase.execute(new SearchResultsSubscriber());
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
        subscriptionsHandler.unsubscribe();
        view = null;
    }

    private final class SearchResultsSubscriber extends DefaultSubscriber<VideoModelsWrapper> {

        @Override
        public void onNext(VideoModelsWrapper videoModelsWrapper) {
            super.onNext(videoModelsWrapper);

            if (view != null) {
                if (videoModelsWrapper.getPage() == FIRST_PAGE) {
                    view.initializeAdapter(videoModelsWrapper.getVideoModels());
                } else {
                    view.updateAdapter(videoModelsWrapper.getVideoModels());
                }
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onSearchResErr", e.getLocalizedMessage());
        }
    }
}
