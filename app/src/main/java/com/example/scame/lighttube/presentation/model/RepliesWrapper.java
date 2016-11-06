package com.example.scame.lighttube.presentation.model;


import java.util.List;

public class RepliesWrapper {

    private final List<ReplyModel> replyModels;

    private final int page;

    public RepliesWrapper(List<ReplyModel> replyModels, int page) {
        this.replyModels = replyModels;
        this.page = page;
    }

    public List<ReplyModel> getReplyModels() {
        return replyModels;
    }

    public int getPage() {
        return page;
    }
}
