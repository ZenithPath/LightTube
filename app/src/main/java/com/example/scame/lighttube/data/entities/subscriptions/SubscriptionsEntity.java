package com.example.scame.lighttube.data.entities.subscriptions;


import java.util.ArrayList;
import java.util.List;

public class SubscriptionsEntity {

    private String nextPageToken;

    private List<SubscriptionItem> items;

    public SubscriptionsEntity() {
        items = new ArrayList<>();
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public void setItems(List<SubscriptionItem> items) {
        this.items = items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public List<SubscriptionItem> getItems() {
        return items;
    }
}
