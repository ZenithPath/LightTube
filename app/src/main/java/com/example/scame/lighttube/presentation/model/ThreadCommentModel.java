package com.example.scame.lighttube.presentation.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ThreadCommentModel implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.replies);
        dest.writeString(this.threadId);
        dest.writeInt(this.replyCount);
        dest.writeString(this.textDisplay);
        dest.writeString(this.profileImageUrl);
        dest.writeString(this.authorName);
        dest.writeString(this.date);
        dest.writeString(this.authorChannelId);
    }

    protected ThreadCommentModel(Parcel in) {
        this.replies = in.createTypedArrayList(ReplyModel.CREATOR);
        this.threadId = in.readString();
        this.replyCount = in.readInt();
        this.textDisplay = in.readString();
        this.profileImageUrl = in.readString();
        this.authorName = in.readString();
        this.date = in.readString();
        this.authorChannelId = in.readString();
    }

    public static final Parcelable.Creator<ThreadCommentModel> CREATOR = new Parcelable.Creator<ThreadCommentModel>() {
        @Override
        public ThreadCommentModel createFromParcel(Parcel source) {
            return new ThreadCommentModel(source);
        }

        @Override
        public ThreadCommentModel[] newArray(int size) {
            return new ThreadCommentModel[size];
        }
    };
}
