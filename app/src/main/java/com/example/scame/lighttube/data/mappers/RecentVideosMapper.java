package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.presentation.model.VideoModel;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import java.util.ArrayList;
import java.util.List;

public class RecentVideosMapper {

    private SearchListMapper searchListMapper;

    public RecentVideosMapper(SearchListMapper searchListMapper) {
        this.searchListMapper = searchListMapper;
    }

    public VideoModelsWrapper convert(List<SearchEntity> searchEntities, int page) {
        List<VideoModel> videoModels = new ArrayList<>();

        for (SearchEntity searchEntity : searchEntities) {
            videoModels.addAll(searchListMapper.convert(searchEntity, page).getVideoModels());
        }
        return new VideoModelsWrapper(videoModels, page);
    }
}
