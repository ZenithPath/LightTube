package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.presentation.model.VideoModel;
import com.example.scame.lighttube.presentation.model.VideoModelsWrapper;

import java.util.Date;

public class PublishingDateParser {

    public VideoModelsWrapper parse(VideoModelsWrapper videoModelsWrapper) {
        for (VideoModel model : videoModelsWrapper.getVideoModels()) {
            model.setDate(parseDateString(model.getPublishedAt()));
        }
        return videoModelsWrapper;
    }

    private Date parseDateString(String publishedAt) {
        int year = Integer.valueOf(publishedAt.substring(0, 4));
        int month = Integer.valueOf(publishedAt.substring(5, 7));
        int day = Integer.valueOf(publishedAt.substring(8, 10));

        int hour = Integer.valueOf(publishedAt.substring(11, 13));
        int min = Integer.valueOf(publishedAt.substring(14, 16));
        int sec = Integer.valueOf(publishedAt.substring(17, 19));

        return new Date(year, month, day, hour, min, sec);
    }
}
