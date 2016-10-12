package com.example.scame.lighttube.data.entities.comments.requests;


public class ThreadRequestSnippet {

    private String videoId;

    private TopLevelComment topLevelComment;

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setTopLevelComment(TopLevelComment topLevelComment) {
        this.topLevelComment = topLevelComment;
    }

    public TopLevelComment getTopLevelComment() {
        return topLevelComment;
    }

    public String getVideoId() {
        return videoId;
    }
}
