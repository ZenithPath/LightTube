package com.example.scame.lighttube.data.entities.comments.requests;



public class ReplyRequestSnippet {

    private String parentId;

    private String textOriginal;

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setTextOriginal(String textOriginal) {
        this.textOriginal = textOriginal;
    }

    public String getParentId() {
        return parentId;
    }

    public String getTextOriginal() {
        return textOriginal;
    }
}
