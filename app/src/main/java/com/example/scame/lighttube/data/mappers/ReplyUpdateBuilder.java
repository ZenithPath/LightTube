package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.comments.requests.UpdateReplyBody;
import com.example.scame.lighttube.data.entities.comments.requests.UpdateReplySnippet;

public class ReplyUpdateBuilder {

    public UpdateReplyBody build(String updatedText) {
        UpdateReplyBody updateReplyBody = new UpdateReplyBody();
        UpdateReplySnippet updateReplySnippet = new UpdateReplySnippet();

        updateReplySnippet.setTextOriginal(updatedText);
        updateReplyBody.setSnippet(updateReplySnippet);

        return updateReplyBody;
    }
}
