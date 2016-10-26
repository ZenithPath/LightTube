package com.example.scame.lighttube.presentation.model;


import java.util.ArrayList;
import java.util.List;

public class ThreadCommentModel {

    private List<ReplyModel> replies;

    private String threadId;

    private int replyCount;

    private String textDisplay;

    private String profileImageUrl;

    private String authorName;

    private String date;

    private String authorChannelId;

    public ThreadCommentModel(ThreadCommentModel threadCommentModel) {
        this.setAuthorChannelId(threadCommentModel.getAuthorChannelId());
        this.setThreadId(threadCommentModel.getThreadId());
        this.setTextDisplay(threadCommentModel.getTextDisplay());
        this.setAuthorName(threadCommentModel.getAuthorName());
        this.setDate(threadCommentModel.getDate());
        this.setProfileImageUrl(threadCommentModel.getProfileImageUrl());
        this.setReplies(threadCommentModel.getReplies());
        this.setReplyCount(threadCommentModel.getReplyCount());
    }

    public ThreadCommentModel() {
        replies = new ArrayList<>();
    }

    public void setAuthorChannelId(String authorChannelId) {
        this.authorChannelId = authorChannelId;
    }

    public void addReply(ReplyModel replyModel) {
        replies.add(replyModel);
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public void setReplies(List<ReplyModel> replies) {
        this.replies = replies;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public void setTextDisplay(String textDisplay) {
        this.textDisplay = textDisplay;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setReply(int index, ReplyModel replyModel) {
        this.replies.set(index, replyModel);
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthorChannelId() {
        return authorChannelId;
    }

    public List<ReplyModel> getReplies() {
        return replies;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public String getTextDisplay() {
        return textDisplay;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getDate() {
        return date;
    }

    public String getThreadId() {
        return threadId;
    }
}
