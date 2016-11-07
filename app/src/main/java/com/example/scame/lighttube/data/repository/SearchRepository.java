package com.example.scame.lighttube.data.repository;

import com.example.scame.lighttube.data.entities.search.AutocompleteEntity;
import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import rx.Observable;

public interface SearchRepository {

    Observable<AutocompleteEntity> autocomplete(String query);

    Observable<VideoModelsWrapper> search(String query, int page);

    Observable<SearchEntity> searchByCategory(String categoryId, String duration, int page);
}
