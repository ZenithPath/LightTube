package com.example.scame.lighttubex.data.repository;

import com.example.scame.lighttubex.data.entities.search.AutocompleteEntity;
import com.example.scame.lighttubex.data.entities.search.SearchEntity;

import rx.Observable;

public interface ISearchDataManager {

    Observable<AutocompleteEntity> autocomplete(String query);

    Observable<SearchEntity> search(String query, int page);
}
