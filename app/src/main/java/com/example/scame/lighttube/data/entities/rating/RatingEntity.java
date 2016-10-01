package com.example.scame.lighttube.data.entities.rating;


import java.util.ArrayList;
import java.util.List;

public class RatingEntity {

    private List<RatingItem> items;

    public RatingEntity() {
        items = new ArrayList<>();
    }

    public void setItems(List<RatingItem> items) {
        this.items = items;
    }

    public List<RatingItem> getItems() {
        return items;
    }
}
