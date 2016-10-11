package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.replies.ReplyEntity;
import com.example.scame.lighttube.data.entities.replies.ReplyItem;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;

public class ReplyListMapper {

    public ReplyListModel convert(ReplyEntity replyEntity) {
        ReplyListModel replyListModel = new ReplyListModel();

        for (ReplyItem replyItem : replyEntity.getItems()) {
            ReplyModel replyModel = new ReplyModel();

            replyModel.setDate(replyItem.getSnippet().getUpdatedAt());
            replyModel.setAuthorName(replyItem.getSnippet().getAuthorDisplayName());
            replyModel.setProfileImageUrl(replyItem.getSnippet().getAuthorProfileImageUrl());
            replyModel.setTextDisplay(replyItem.getSnippet().getTextDisplay());
            replyModel.setParentId(replyItem.getSnippet().getParentId());
            replyModel.setCommentId(replyItem.getId());

            replyListModel.addReplyModel(replyModel);
        }

        return replyListModel;
    }
}
