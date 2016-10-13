package com.example.scame.lighttube.presentation.model;


import java.util.ArrayList;
import java.util.List;

public class ReplyListModel {

    private List<ReplyModel> replyModels;

    public ReplyListModel() {
        replyModels = new ArrayList<>();
    }

    public void addReplyModel(ReplyModel replyModel) {
        replyModels.add(replyModel);
    }

    public void addReplyModel(int index, ReplyModel replyModel) {
        replyModels.add(index, replyModel);
    }

    public ReplyModel getReplyModel(int position) {
        return replyModels.get(position);
    }

    public void setReplyModels(List<ReplyModel> replyModels) {
        this.replyModels = replyModels;
    }

    public List<ReplyModel> getReplyModels() {
        return replyModels;
    }
}
