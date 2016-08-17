package com.example.scame.lighttube.data.repository;

import com.example.scame.lighttube.data.entities.search.AutocompleteEntity;
import com.example.scame.lighttube.data.entities.search.SearchEntity;

import rx.Observable;

public interface ISearchDataManager {

    Observable<AutocompleteEntity> autocomplete(String query);

    Observable<SearchEntity> search(String query, int page);
}
