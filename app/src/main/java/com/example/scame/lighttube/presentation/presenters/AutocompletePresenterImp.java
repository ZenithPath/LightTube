package com.example.scame.lighttube.presentation.presenters;

import android.util.Log;

import com.example.scame.lighttube.domain.usecases.GetAutocompleteListUseCase;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;

import java.util.List;

public class AutocompletePresenterImp<V extends AutocompletePresenter.AutocompleteView>
                                            implements AutocompletePresenter<V> {

    private V view;

    private GetAutocompleteListUseCase useCase;

    private SubscriptionsHandler subscriptionsHandler;

    public AutocompletePresenterImp(GetAutocompleteListUseCase useCase, SubscriptionsHandler subscriptionsHandler) {
        this.subscriptionsHandler = subscriptionsHandler;
        this.useCase = useCase;
    }

    @Override
    public void setView(V view) {
        this.view = view;
    }

    public void updateAutocompleteList(String query) {
        useCase.setQuery(query);
        useCase.execute(new AutocompleteListSubscriber());
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

    private final class AutocompleteListSubscriber extends DefaultSubscriber<List<String>> {

        @Override
        public void onNext(List<String> strings) {
            super.onNext(strings);

            if (view != null) {
                view.updateAutocompleteList(strings);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxAutocompleteErr", e.getLocalizedMessage());
        }
    }
}
