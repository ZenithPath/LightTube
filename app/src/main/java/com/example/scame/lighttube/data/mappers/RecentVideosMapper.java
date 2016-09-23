package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class RecentVideosMapper {

    private SearchListMapper searchListMapper;

    public RecentVideosMapper(SearchListMapper searchListMapper) {
        this.searchListMapper = searchListMapper;
    }

    public List<VideoModel> convert(List<SearchEntity> searchEntities) {
        List<VideoModel> videoModels = new ArrayList<>();

        for (SearchEntity searchEntity : searchEntities) {
            videoModels.addAll(searchListMapper.convert(searchEntity));
        }

        return videoModels;
    }
}
