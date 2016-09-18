package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionItem;
import com.example.scame.lighttube.data.entities.subscriptions.SubscriptionsEntity;
import com.example.scame.lighttube.presentation.model.ChannelModel;

import java.util.ArrayList;
import java.util.List;

public class ChannelsMapper {

    public List<ChannelModel> convert(SubscriptionsEntity subscriptionsEntity) {
        List<ChannelModel> channelModels = new ArrayList<>();

        for (SubscriptionItem item : subscriptionsEntity.getItems()) {
            ChannelModel channelModel = new ChannelModel();

            channelModel.setChannelId(item.getSnippet().getResourceId().getChannelId());
            channelModel.setImageUrl(item.getSnippet().getThumbnails().getHigh().getUrl());
            channelModel.setTitle(item.getSnippet().getTitle());

            channelModels.add(channelModel);
        }

        return channelModels;
    }
}
