package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.presentation.model.SearchItemModel;

import java.util.ArrayList;
import java.util.List;

public class RecentVideosMapper {

    public List<SearchItemModel> convert(List<SearchEntity> searchEntities) {
        List<SearchItemModel> searchItems = new ArrayList<>();
        SearchListMapper mapper = new SearchListMapper();

        for (SearchEntity searchEntity : searchEntities) {
            searchItems.addAll(mapper.convert(searchEntity));
        }

        return searchItems;
    }
}
