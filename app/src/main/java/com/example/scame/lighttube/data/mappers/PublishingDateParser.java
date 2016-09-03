package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.presentation.model.SearchItemModel;

import java.util.Date;
import java.util.List;

public class PublishingDateParser {

    public List<SearchItemModel> parse(List<SearchItemModel> searchItems) {

        for (SearchItemModel item : searchItems) {
            item.setDate(parseDateString(item.getPublishedAt()));
        }

        return searchItems;
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
