package com.example.scame.lighttubex.presentation.di.modules;

import android.app.Activity;

import com.example.scame.lighttubex.data.repository.ISearchDataManager;
import com.example.scame.lighttubex.domain.schedulers.ObserveOn;
import com.example.scame.lighttubex.domain.schedulers.SubscribeOn;
import com.example.scame.lighttubex.domain.usecases.AutocompleteListUseCase;
import com.example.scame.lighttubex.domain.usecases.SearchUseCase;
import com.example.scame.lighttubex.presentation.di.PerActivity;
import com.example.scame.lighttubex.presentation.presenters.AutocompletePresenterImp;
import com.example.scame.lighttubex.presentation.presenters.IAutocompletePresenter;
import com.example.scame.lighttubex.presentation.presenters.ISearchResultsPresenter;
import com.example.scame.lighttubex.presentation.presenters.SearchResultsPresenterImp;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttubex.presentation.presenters.IAutocompletePresenter.AutocompleteView;
import static com.example.scame.lighttubex.presentation.presenters.ISearchResultsPresenter.SearchResultsView;

@Module
public class SearchModule {

    private Activity activity;

    public SearchModule(Activity activity) {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    AutocompleteListUseCase provideAutocompleteListUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                           ISearchDataManager dataManager) {

        return new AutocompleteListUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    IAutocompletePresenter<AutocompleteView> provideAutocompletePresenter(AutocompleteListUseCase useCase) {
        return new AutocompletePresenterImp<>(useCase);
    }

    @PerActivity
    @Provides
    SearchUseCase provideSearchUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                       ISearchDataManager dataManager) {

        return new SearchUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    ISearchResultsPresenter<SearchResultsView> provideSearchResultsPresenter(SearchUseCase useCase) {
        return new SearchResultsPresenterImp<>(useCase);
    }
}
