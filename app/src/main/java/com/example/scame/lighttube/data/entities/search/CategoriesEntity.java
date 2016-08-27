package com.example.scame.lighttube.data.entities.search;


import java.util.ArrayList;
import java.util.List;

public class CategoriesEntity {

    private List<CategoryItem> items;

    public CategoriesEntity() {
        items = new ArrayList<>();
    }

    public void setItems(List<CategoryItem> items) {
        this.items = items;
    }

    public List<CategoryItem> getItems() {
        return items;
    }
}
