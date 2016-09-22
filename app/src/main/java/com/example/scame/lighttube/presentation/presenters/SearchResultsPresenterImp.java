package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.ContentDetailsUseCase;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.SearchUseCase;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

public class SearchResultsPresenterImp<V extends ISearchResultsPresenter.SearchResultsView>
                                                    implements ISearchResultsPresenter<V> {

    private static final int FIRST_PAGE = 0;

    private SearchUseCase searchUseCase;

    private ContentDetailsUseCase detailsUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private V view;

    private int page;

    public SearchResultsPresenterImp(SearchUseCase searchUseCase, ContentDetailsUseCase detailsUseCase,
                                     SubscriptionsHandler subscriptionsHandler) {
        this.searchUseCase = searchUseCase;
        this.detailsUseCase = detailsUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void fetchVideos(int page, String query) {
        this.page = page;
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

    private final class SearchResultsSubscriber extends DefaultSubscriber<List<VideoModel>> {

        @Override
        public void onNext(List<VideoModel> videoModels) {
            super.onNext(videoModels);

            detailsUseCase.setVideoModels(videoModels);
            detailsUseCase.execute(new ContentDetailsSubscriber());
        }
    }

    private final class ContentDetailsSubscriber extends DefaultSubscriber<List<VideoModel>> {

        @Override
        public void onNext(List<VideoModel> videoModels) {
            super.onNext(videoModels);

            if (page == FIRST_PAGE) {
                view.initializeAdapter(videoModels);
            } else {
                view.updateAdapter(videoModels);
            }
        }
    }
}
