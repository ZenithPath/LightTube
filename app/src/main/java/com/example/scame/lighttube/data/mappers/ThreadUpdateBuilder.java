package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.comments.requests.ThreadCommentBody;
import com.example.scame.lighttube.data.entities.comments.requests.ThreadRequestSnippet;
import com.example.scame.lighttube.data.entities.comments.requests.TopLevelComment;
import com.example.scame.lighttube.data.entities.comments.requests.TopLevelCommentSnippet;

public class ThreadUpdateBuilder {

    public ThreadCommentBody build(String updatedText, String commentId) {
        ThreadCommentBody threadCommentBody = new ThreadCommentBody();
        threadCommentBody.setId(commentId);

        ThreadRequestSnippet threadRequestSnippet = new ThreadRequestSnippet();
        TopLevelComment topLevelComment = new TopLevelComment();
        TopLevelCommentSnippet topLevelCommentSnippet = new TopLevelCommentSnippet();
        topLevelCommentSnippet.setTextOriginal(updatedText);

        topLevelComment.setSnippet(topLevelCommentSnippet);
        threadRequestSnippet.setTopLevelComment(topLevelComment);
        threadCommentBody.setSnippet(threadRequestSnippet);

        return threadCommentBody;
    }
}
