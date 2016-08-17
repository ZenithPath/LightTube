package com.example.scame.lighttube.data.mappers;

import com.example.scame.lighttube.data.entities.videolist.VideoEntity;
import com.example.scame.lighttube.data.entities.videolist.VideoEntityList;
import com.example.scame.lighttube.presentation.model.VideoItemModel;

import java.util.ArrayList;
import java.util.List;

public class VideoListMapper {

    public List<VideoItemModel> convert(VideoEntityList entitiesList) {
        List<VideoItemModel> modelList = new ArrayList<>();

        for (VideoEntity entity : entitiesList.getItems()) {
            VideoItemModel itemModel = new VideoItemModel();

            itemModel.setImageUrl(entity.getSnippet().getThumbnails().getHigh().getUrl());
            itemModel.setTitle(entity.getSnippet().getTitle());
            itemModel.setId(entity.getId());

            modelList.add(itemModel);
        }

        return modelList;
    }
}
