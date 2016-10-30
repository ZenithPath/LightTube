package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.comments.responses.CommentItem;
import com.example.scame.lighttube.data.entities.comments.responses.CommentSnippet;
import com.example.scame.lighttube.data.entities.comments.responses.CommentSnippetHolder;
import com.example.scame.lighttube.data.entities.comments.responses.CommentThreadsEntity;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.ArrayList;
import java.util.List;

public class CommentListMapper {

    public List<ThreadCommentModel> convert(CommentThreadsEntity commentThreadsEntity) {
        List<ThreadCommentModel> modelsList = new ArrayList<>(commentThreadsEntity.getItems().size());

        for (CommentItem commentItem : commentThreadsEntity.getItems()) {
            ThreadCommentModel threadCommentModel = new ThreadCommentModel();
            CommentSnippet commentSnippet = commentItem.getSnippet().getTopLevelComment().getSnippet();

            threadCommentModel.setAuthorName(commentSnippet.getAuthorDisplayName());
            threadCommentModel.setDate(commentSnippet.getUpdatedAt());
            threadCommentModel.setProfileImageUrl(commentSnippet.getAuthorProfileImageUrl());
            threadCommentModel.setReplyCount(commentItem.getSnippet().getTotalReplyCount());
            threadCommentModel.setTextDisplay(commentSnippet.getTextDisplay());
            threadCommentModel.setThreadId(commentItem.getSnippet().getTopLevelComment().getId());
            threadCommentModel.setAuthorChannelId(commentSnippet.getAuthorChannelId().getValue());

            if (commentItem.getReplies() != null) {
                addReplies(commentItem, threadCommentModel);
            }

            modelsList.add(threadCommentModel);
        }
        return modelsList;
    }

    private void addReplies(CommentItem commentItem, ThreadCommentModel threadCommentModel) {

        for (CommentSnippetHolder commentSnippetHolder : commentItem.getReplies().getComments()) {
            ReplyModel replyModel = new ReplyModel();
            CommentSnippet commentSnippet = commentSnippetHolder.getSnippet();

            replyModel.setParentId(commentItem.getId());
            replyModel.setCommentId(commentSnippetHolder.getId());
            replyModel.setTextDisplay(commentSnippet.getTextDisplay());
            replyModel.setProfileImageUrl(commentSnippet.getAuthorProfileImageUrl());
            replyModel.setAuthorName(commentSnippet.getAuthorDisplayName());
            replyModel.setDate(commentSnippet.getUpdatedAt());
            replyModel.setAuthorChannelId(commentSnippet.getAuthorChannelId().getValue());

            threadCommentModel.addReply(replyModel);
        }
    }
}
