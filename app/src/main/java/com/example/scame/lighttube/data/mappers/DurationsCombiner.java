package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.content.ContentEntity;
import com.example.scame.lighttube.data.entities.content.ContentItem;
import com.example.scame.lighttube.presentation.model.VideoModel;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import java.util.List;

public class DurationsCombiner {

    public VideoModelsWrapper combine(ContentEntity contentEntity, VideoModelsWrapper modelsWrapper) {
        List<ContentItem> contentItems = contentEntity.getItems();
        List<VideoModel> videoModels = modelsWrapper.getVideoModels();

        for (int i = 0; i < videoModels.size(); i++) {
            videoModels.get(i).setDuration(contentItems.get(i).getContentDetails().getDuration());
        }
        return new VideoModelsWrapper(videoModels, modelsWrapper.getPage());
    }
}
