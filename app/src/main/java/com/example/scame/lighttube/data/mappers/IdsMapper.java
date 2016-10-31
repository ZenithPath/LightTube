package com.example.scame.lighttube.data.mappers;

import android.util.Log;

import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

public class IdsMapper {

    public String convert(List<VideoModel> videoModels) {
        StringBuilder builder = new StringBuilder(videoModels.size());
        Log.i("onxGonnaMapIds", videoModels.size() + "");
        for (VideoModel videoModel : videoModels) {
            builder.append(videoModel.getVideoId()).append("%2C");
        }

        Log.i("onxBeforeClip", builder.toString());
        builder.delete(builder.length() - 3, builder.length());

        Log.i("onxFinalString", builder.toString());
        return builder.toString();
    }
}
