package com.example.scame.lighttubex.data.mappers;

import com.example.scame.lighttubex.data.entities.search.SearchEntity;
import com.example.scame.lighttubex.data.entities.search.SearchItem;
import com.example.scame.lighttubex.presentation.model.SearchItemModel;

import java.util.ArrayList;
import java.util.List;

public class SearchListMapper {

    public List<SearchItemModel> convert(SearchEntity searchEntity) {
        List<SearchItemModel> items = new ArrayList<>();

        for (SearchItem searchItem : searchEntity.getItems()) {
            SearchItemModel modelItem = new SearchItemModel();

            modelItem.setId(searchItem.getId().getVideoId());
            modelItem.setTitle(searchItem.getSnippet().getTitle());
            modelItem.setImageUrl(searchItem.getSnippet().getThumbnails().getMedium().getUrl());

            items.add(modelItem);
        }

        return items;
    }
}
