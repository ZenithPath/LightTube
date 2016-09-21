package com.example.scame.lighttube.data.entities.content;

public class ContentItem {

    private ContentDetails contentDetails;

    private String id;

    public void setContentDetails(ContentDetails contentDetails) {
        this.contentDetails = contentDetails;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ContentDetails getContentDetails() {
        return contentDetails;
    }

    public String getId() {
        return id;
    }
}
