package com.example.scame.lighttube.data.mappers;

import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.entities.search.SearchItem;
import com.example.scame.lighttube.presentation.model.VideoModel;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import java.util.ArrayList;
import java.util.List;

public class SearchListMapper {

    public VideoModelsWrapper convert(SearchEntity searchEntity, int page) {
        List<VideoModel> videoModels = new ArrayList<>();

        for (SearchItem searchItem : searchEntity.getItems()) {
            VideoModel videoModel = new VideoModel();

            videoModel.setVideoId(searchItem.getId().getVideoId());
            videoModel.setTitle(searchItem.getSnippet().getTitle());
            videoModel.setImageUrl(searchItem.getSnippet().getThumbnails().getHigh().getUrl());
            videoModel.setPublishedAt(searchItem.getSnippet().getPublishedAt());
            videoModel.setChannelTitle(searchItem.getSnippet().getChannelTitle());
            videoModel.setChannelId(searchItem.getSnippet().getChannelId());

            videoModels.add(videoModel);
        }

        return new VideoModelsWrapper(videoModels, page);
    }
}
