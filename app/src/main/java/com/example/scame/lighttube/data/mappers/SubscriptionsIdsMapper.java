package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionItem;
import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionsEntity;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionsIdsMapper {

    public List<String> convert(SubscriptionsEntity entity) {
        List<String> items = new ArrayList<>();

        for (SubscriptionItem item : entity.getItems()) {
            items.add(item.getSnippet().getResourceId().getChannelId());
        }

        return items;
    }
}
