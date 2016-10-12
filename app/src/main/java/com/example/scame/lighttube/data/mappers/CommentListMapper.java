package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.comments.responses.CommentItem;
import com.example.scame.lighttube.data.entities.comments.responses.CommentSnippetHolder;
import com.example.scame.lighttube.data.entities.comments.responses.CommentThreadsEntity;
import com.example.scame.lighttube.presentation.model.CommentListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

public class CommentListMapper {

    public CommentListModel convert(CommentThreadsEntity commentThreadsEntity) {
        CommentListModel commentListModel = new CommentListModel();

        for (CommentItem commentItem : commentThreadsEntity.getItems()) {
            ThreadCommentModel threadCommentModel = new ThreadCommentModel();

            threadCommentModel.setAuthorName(commentItem.getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName());
            threadCommentModel.setDate(commentItem.getSnippet().getTopLevelComment().getSnippet().getUpdatedAt());
            threadCommentModel.setProfileImageUrl(commentItem.getSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl());
            threadCommentModel.setReplyCount(commentItem.getSnippet().getTotalReplyCount());
            threadCommentModel.setTextDisplay(commentItem.getSnippet().getTopLevelComment().getSnippet().getTextDisplay());
            threadCommentModel.setThreadId(commentItem.getSnippet().getTopLevelComment().getId());

            if (commentItem.getReplies() != null) {
                addReplies(commentItem, threadCommentModel);
            }

            commentListModel.addThreadCommentModel(threadCommentModel);
        }

        return commentListModel;
    }

    private void addReplies(CommentItem commentItem, ThreadCommentModel threadCommentModel) {

        for (CommentSnippetHolder commentSnippetHolder : commentItem.getReplies().getComments()) {
            ReplyModel replyModel = new ReplyModel();

            replyModel.setParentId(commentItem.getId());
            replyModel.setCommentId(commentSnippetHolder.getId());
            replyModel.setTextDisplay(commentSnippetHolder.getSnippet().getTextDisplay());
            replyModel.setProfileImageUrl(commentSnippetHolder.getSnippet().getAuthorProfileImageUrl());
            replyModel.setAuthorName(commentSnippetHolder.getSnippet().getAuthorDisplayName());
            replyModel.setDate(commentSnippetHolder.getSnippet().getUpdatedAt());

            threadCommentModel.addReply(replyModel);
        }
    }
}
