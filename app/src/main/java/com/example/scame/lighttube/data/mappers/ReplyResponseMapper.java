package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.comments.responses.CommentSnippet;
import com.example.scame.lighttube.data.entities.comments.responses.CommentSnippetHolder;
import com.example.scame.lighttube.presentation.model.ReplyModel;

public class ReplyResponseMapper {

    public ReplyModel convert(CommentSnippetHolder snippetHolder) {
        ReplyModel replyModel = new ReplyModel();
        CommentSnippet commentSnippet = snippetHolder.getSnippet();

        replyModel.setParentId(commentSnippet.getParentId());
        replyModel.setCommentId(snippetHolder.getId());
        replyModel.setTextDisplay(commentSnippet.getTextOriginal());
        replyModel.setProfileImageUrl(commentSnippet.getAuthorProfileImageUrl());
        replyModel.setAuthorName(commentSnippet.getAuthorDisplayName());
        replyModel.setDate(commentSnippet.getUpdatedAt());
        // reply response doesn't contain author channel id field

        return replyModel;
    }
}
