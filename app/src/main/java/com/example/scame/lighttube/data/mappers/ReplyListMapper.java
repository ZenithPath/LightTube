package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.replies.ReplyEntity;
import com.example.scame.lighttube.data.entities.replies.ReplyItem;
import com.example.scame.lighttube.data.entities.replies.ReplySnippet;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;

public class ReplyListMapper {

    public ReplyListModel convert(ReplyEntity replyEntity) {
        ReplyListModel replyListModel = new ReplyListModel();

        for (ReplyItem replyItem : replyEntity.getItems()) {
            ReplyModel replyModel = new ReplyModel();
            ReplySnippet replySnippet = replyItem.getSnippet();

            replyModel.setDate(replySnippet.getUpdatedAt());
            replyModel.setAuthorName(replySnippet.getAuthorDisplayName());
            replyModel.setProfileImageUrl(replySnippet.getAuthorProfileImageUrl());
            replyModel.setTextDisplay(replySnippet.getTextDisplay());
            replyModel.setParentId(replySnippet.getParentId());
            replyModel.setCommentId(replyItem.getId());
            replyModel.setAuthorChannelId(replySnippet.getAuthorChannelId().getValue());

            replyListModel.addReplyModel(replyModel);
        }

        return replyListModel;
    }
}
