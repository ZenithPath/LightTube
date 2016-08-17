package com.example.scame.lighttube.data.entities.search;


import java.util.List;

public class AutocompleteEntity {

    private List<String> items;

    private String searchQuery;

    public void setItems(List<String> items) {
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery() {
        return searchQuery;
    }
}
