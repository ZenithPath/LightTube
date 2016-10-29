package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.replies.ReplyEntity;
import com.example.scame.lighttube.data.entities.replies.ReplyItem;
import com.example.scame.lighttube.data.entities.replies.ReplySnippet;
import com.example.scame.lighttube.presentation.model.ReplyModel;

import java.util.ArrayList;
import java.util.List;

public class ReplyListMapper {

    public List<ReplyModel> convert(ReplyEntity replyEntity) {
        List<ReplyModel> repliesList = new ArrayList<>(replyEntity.getItems().size());

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

            repliesList.add(replyModel);
        }

        return repliesList;
    }
}
