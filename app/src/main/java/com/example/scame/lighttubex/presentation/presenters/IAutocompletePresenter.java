package com.example.scame.lighttubex.presentation.presenters;


import java.util.List;

public interface IAutocompletePresenter<V> extends Presenter<V> {

    interface AutocompleteView {

        void updateAutocompleteList(List<String> strings);
    }

    void updateAutocompleteList(String query);
}
