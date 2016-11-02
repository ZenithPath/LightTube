package com.example.scame.lighttube.data.mappers;

import com.example.scame.lighttube.data.entities.search.SearchEntity;
import com.example.scame.lighttube.data.entities.search.SearchItem;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class SearchListMapper {

    public List<VideoModel> convert(SearchEntity searchEntity) {
        List<VideoModel> items = new ArrayList<>();

        for (SearchItem searchItem : searchEntity.getItems()) {
            VideoModel videoModel = new VideoModel();

            videoModel.setVideoId(searchItem.getId().getVideoId());
            videoModel.setTitle(searchItem.getSnippet().getTitle());
            videoModel.setImageUrl(searchItem.getSnippet().getThumbnails().getHigh().getUrl());
            videoModel.setPublishedAt(searchItem.getSnippet().getPublishedAt());
            videoModel.setChannelTitle(searchItem.getSnippet().getChannelTitle());
            videoModel.setChannelId(searchItem.getSnippet().getChannelId());

            items.add(videoModel);
        }

        return items;
    }
}
