package com.example.scame.lighttube.data.mappers;


import android.util.Log;

import com.example.scame.lighttube.data.entities.content.ContentEntity;
import com.example.scame.lighttube.data.entities.content.ContentItem;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

public class DurationsCombiner {

    public List<VideoModel> combine(ContentEntity contentEntity, List<VideoModel> videoModels) {
        List<ContentItem> contentItems = contentEntity.getItems();
        Log.i("onxCombinerVideos", videoModels.size() + "");
        Log.i("onxItems", contentItems.size() + "");
        for (int i = 0; i < videoModels.size(); i++) {
            videoModels.get(i).setDuration(contentItems.get(i).getContentDetails().getDuration());
        }

        return videoModels;
    }
}
