package com.example.scame.lighttube.data.repository;

import com.example.scame.lighttube.data.entities.search.AutocompleteEntity;
import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public interface ISearchDataManager {

    Observable<AutocompleteEntity> autocomplete(String query);

    Observable<List<VideoModel>> search(String query, int page);

    Observable<SearchEntity> searchByCategory(String categoryId, String duration, int page);
}
