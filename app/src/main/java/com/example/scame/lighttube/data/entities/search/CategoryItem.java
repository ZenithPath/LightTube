package com.example.scame.lighttube.data.entities.search;


public class CategoryItem {

    private String id;

    private CategorySnippet snippet;

    public void setSnippet(CategorySnippet snippet) {
        this.snippet = snippet;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public CategorySnippet getSnippet() {
        return snippet;
    }
}
