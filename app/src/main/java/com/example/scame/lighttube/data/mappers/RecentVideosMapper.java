package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class RecentVideosMapper {

    public List<VideoModel> convert(List<SearchEntity> searchEntities) {
        List<VideoModel> videoModels = new ArrayList<>();
        SearchListMapper mapper = new SearchListMapper();

        for (SearchEntity searchEntity : searchEntities) {
            videoModels.addAll(mapper.convert(searchEntity));
        }

        return videoModels;
    }
}
