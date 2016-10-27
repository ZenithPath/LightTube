package com.example.scame.lighttube.presentation.model;


import android.os.Parcel;
import android.os.Parcelable;

public class ReplyModel implements Parcelable {

    private String textDisplay;

    private String profileImageUrl;

    private String authorName;

    private String date;

    private String parentId;

    private String commentId;

    private String authorChannelId;

    public void setAuthorChannelId(String authorChannelId) {
        this.authorChannelId = authorChannelId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public void setTextDisplay(String textDisplay) {
        this.textDisplay = textDisplay;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCommentId() {
        return commentId;
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

    public String getParentId() {
        return parentId;
    }

    public String getAuthorChannelId() {
        return authorChannelId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.textDisplay);
        dest.writeString(this.profileImageUrl);
        dest.writeString(this.authorName);
        dest.writeString(this.date);
        dest.writeString(this.parentId);
        dest.writeString(this.commentId);
        dest.writeString(this.authorChannelId);
    }

    public ReplyModel() {
    }

    protected ReplyModel(Parcel in) {
        this.textDisplay = in.readString();
        this.profileImageUrl = in.readString();
        this.authorName = in.readString();
        this.date = in.readString();
        this.parentId = in.readString();
        this.commentId = in.readString();
        this.authorChannelId = in.readString();
    }

    public static final Parcelable.Creator<ReplyModel> CREATOR = new Parcelable.Creator<ReplyModel>() {
        @Override
        public ReplyModel createFromParcel(Parcel source) {
            return new ReplyModel(source);
        }

        @Override
        public ReplyModel[] newArray(int size) {
            return new ReplyModel[size];
        }
    };
}
