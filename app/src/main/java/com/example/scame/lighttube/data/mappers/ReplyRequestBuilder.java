package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.comments.requests.ReplyRequestBody;
import com.example.scame.lighttube.data.entities.comments.requests.ReplyRequestSnippet;

public class ReplyRequestBuilder {

    public ReplyRequestBody build(String parentId, String replyText) {
        ReplyRequestBody replyRequestBody = new ReplyRequestBody();
        ReplyRequestSnippet replyRequestSnippet = new ReplyRequestSnippet();

        replyRequestSnippet.setTextOriginal(replyText);
        replyRequestSnippet.setParentId(parentId);
        replyRequestBody.setSnippet(replyRequestSnippet);

        return replyRequestBody;
    }
}
