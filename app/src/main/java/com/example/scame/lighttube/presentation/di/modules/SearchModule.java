package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.IContentDetailsDataManager;
import com.example.scame.lighttube.data.repository.ISearchDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.AutocompleteListUseCase;
import com.example.scame.lighttube.domain.usecases.ContentDetailsUseCase;
import com.example.scame.lighttube.domain.usecases.SearchUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.AutocompletePresenterImp;
import com.example.scame.lighttube.presentation.presenters.IAutocompletePresenter;
import com.example.scame.lighttube.presentation.presenters.ISearchResultsPresenter;
import com.example.scame.lighttube.presentation.presenters.SearchResultsPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.IAutocompletePresenter.AutocompleteView;
import static com.example.scame.lighttube.presentation.presenters.ISearchResultsPresenter.SearchResultsView;

@Module
public class SearchModule {

    @PerActivity
    @Provides
    @Named("autocomplete")
    SubscriptionsHandler provideAutocompleteHandler(AutocompleteListUseCase autocompleteUseCase) {
        return new SubscriptionsHandler(autocompleteUseCase);
    }

    @PerActivity
    @Provides
    @Named("search")
    SubscriptionsHandler provideSearchHandler(SearchUseCase searchUseCase, ContentDetailsUseCase detailsUseCase) {
        return new SubscriptionsHandler(searchUseCase, detailsUseCase);
    }

    @PerActivity
    @Provides
    AutocompleteListUseCase provideAutocompleteListUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                           ISearchDataManager dataManager) {

        return new AutocompleteListUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    IAutocompletePresenter<AutocompleteView> provideAutocompletePresenter(@Named("autocomplete") SubscriptionsHandler handler,
                                                                          AutocompleteListUseCase useCase) {
        return new AutocompletePresenterImp<>(useCase, handler);
    }

    @PerActivity
    @Provides
    SearchUseCase provideSearchUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                       ISearchDataManager dataManager) {

        return new SearchUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    ContentDetailsUseCase provideContentDetailsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                       IContentDetailsDataManager dataManager) {

        return new ContentDetailsUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    ISearchResultsPresenter<SearchResultsView> provideSearchResultsPresenter(SearchUseCase searchUseCase,
                                                                             ContentDetailsUseCase detailsUseCase,
                                                                             @Named("search") SubscriptionsHandler handler) {

        return new SearchResultsPresenterImp<>(searchUseCase, detailsUseCase, handler);
    }
}
