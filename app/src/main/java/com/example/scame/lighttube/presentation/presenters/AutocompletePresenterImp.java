package com.example.scame.lighttube.presentation.presenters;

import com.example.scame.lighttube.domain.usecases.AutocompleteListUseCase;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;

import java.util.List;

public class AutocompletePresenterImp<V extends IAutocompletePresenter.AutocompleteView>
                                            implements IAutocompletePresenter<V> {

    private V view;

    private AutocompleteListUseCase useCase;

    private SubscriptionsHandler subscriptionsHandler;

    public AutocompletePresenterImp(AutocompleteListUseCase useCase, SubscriptionsHandler subscriptionsHandler) {
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

            view.updateAutocompleteList(strings);
        }
    }
}
