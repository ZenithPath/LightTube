package com.example.scame.lighttube.data.entities.content;


import java.util.ArrayList;
import java.util.List;

public class ContentEntity {

    private List<ContentItem> items;

    public ContentEntity() {
        items = new ArrayList<>();
    }

    public void setItems(List<ContentItem> items) {
        this.items = items;
    }

    public List<ContentItem> getItems() {
        return items;
    }
}
