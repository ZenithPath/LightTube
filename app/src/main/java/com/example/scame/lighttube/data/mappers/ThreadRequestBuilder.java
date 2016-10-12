package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.comments.requests.ThreadCommentBody;
import com.example.scame.lighttube.data.entities.comments.requests.ThreadRequestSnippet;
import com.example.scame.lighttube.data.entities.comments.requests.TopLevelComment;
import com.example.scame.lighttube.data.entities.comments.requests.TopLevelCommentSnippet;

public class ThreadRequestBuilder {

    public ThreadCommentBody build(String text, String videoId) {
        ThreadCommentBody threadCommentBody = new ThreadCommentBody();
        ThreadRequestSnippet requestSnippet = new ThreadRequestSnippet();
        TopLevelComment topLevelComment = new TopLevelComment();
        TopLevelCommentSnippet topCommentSnippet = new TopLevelCommentSnippet();

        topCommentSnippet.setTextOriginal(text);
        topLevelComment.setSnippet(topCommentSnippet);
        requestSnippet.setTopLevelComment(topLevelComment);
        requestSnippet.setVideoId(videoId);
        threadCommentBody.setSnippet(requestSnippet);

        return threadCommentBody;
    }
}
