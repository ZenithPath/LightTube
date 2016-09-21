package com.example.scame.lighttube.data.mappers;

import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

public class IdsMapper {

    public String convert(List<VideoModel> videoModels) {
        StringBuilder builder = new StringBuilder(videoModels.size());

        for (VideoModel videoModel : videoModels) {
            builder.append(videoModel.getVideoId()).append("%2C");
        }

        builder.delete(builder.length() - 3, builder.length());

        return builder.toString();
    }
}
