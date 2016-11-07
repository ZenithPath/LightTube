package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.SearchRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.GetAutocompleteListUseCase;
import com.example.scame.lighttube.domain.usecases.SearchUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.AutocompletePresenterImp;
import com.example.scame.lighttube.presentation.presenters.AutocompletePresenter;
import com.example.scame.lighttube.presentation.presenters.SearchResultsPresenter;
import com.example.scame.lighttube.presentation.presenters.SearchResultsPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.AutocompletePresenter.AutocompleteView;
import static com.example.scame.lighttube.presentation.presenters.SearchResultsPresenter.SearchResultsView;

@Module
public class SearchModule {

    @PerActivity
    @Provides
    @Named("autocomplete")
    SubscriptionsHandler provideAutocompleteHandler(GetAutocompleteListUseCase autocompleteUseCase) {
        return new SubscriptionsHandler(autocompleteUseCase);
    }

    @PerActivity
    @Provides
    @Named("search")
    SubscriptionsHandler provideSearchHandler(SearchUseCase searchUseCase) {
        return new SubscriptionsHandler(searchUseCase);
    }

    @PerActivity
    @Provides
    GetAutocompleteListUseCase provideAutocompleteListUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                              SearchRepository dataManager) {
        return new GetAutocompleteListUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    AutocompletePresenter<AutocompleteView> provideAutocompletePresenter(@Named("autocomplete") SubscriptionsHandler handler,
                                                                         GetAutocompleteListUseCase useCase) {
        return new AutocompletePresenterImp<>(useCase, handler);
    }

    @PerActivity
    @Provides
    SearchUseCase provideSearchUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, SearchRepository searchRepository) {
        return new SearchUseCase(subscribeOn, observeOn, searchRepository);
    }


    @PerActivity
    @Provides
    SearchResultsPresenter<SearchResultsView> provideSearchResultsPresenter(SearchUseCase searchUseCase,
                                                                            @Named("search") SubscriptionsHandler handler) {
        return new SearchResultsPresenterImp<>(searchUseCase, handler);
    }
}
