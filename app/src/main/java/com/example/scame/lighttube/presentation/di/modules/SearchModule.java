package com.example.scame.lighttube.presentation.di.modules;

import android.app.Activity;

import com.example.scame.lighttube.data.repository.ISearchDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.AutocompleteListUseCase;
import com.example.scame.lighttube.domain.usecases.SearchUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.AutocompletePresenterImp;
import com.example.scame.lighttube.presentation.presenters.IAutocompletePresenter;
import com.example.scame.lighttube.presentation.presenters.ISearchResultsPresenter;
import com.example.scame.lighttube.presentation.presenters.SearchResultsPresenterImp;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.IAutocompletePresenter.AutocompleteView;
import static com.example.scame.lighttube.presentation.presenters.ISearchResultsPresenter.SearchResultsView;

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
