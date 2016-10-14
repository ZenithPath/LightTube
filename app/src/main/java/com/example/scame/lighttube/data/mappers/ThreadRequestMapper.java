package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.comments.responses.CommentItem;
import com.example.scame.lighttube.data.entities.comments.responses.CommentSnippet;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

public class ThreadRequestMapper {

    public ThreadCommentModel convert(CommentItem commentItem) {
        ThreadCommentModel threadCommentModel = new ThreadCommentModel();

        CommentSnippet commentSnippet = commentItem.getSnippet().getTopLevelComment().getSnippet();
        threadCommentModel.setThreadId(commentItem.getId());
        threadCommentModel.setTextDisplay(commentSnippet.getTextOriginal());
        threadCommentModel.setProfileImageUrl(commentSnippet.getAuthorProfileImageUrl());
        threadCommentModel.setAuthorName(commentSnippet.getAuthorDisplayName());
        threadCommentModel.setDate(commentSnippet.getUpdatedAt());
        threadCommentModel.setAuthorChannelId(commentSnippet.getAuthorChannelId().getValue());

        return threadCommentModel;
    }
}
