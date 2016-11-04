package com.example.scame.lighttube.presentation.model;

import android.os.Parcel;

import java.util.Date;

public class VideoModel implements ModelMarker, Comparable<VideoModel> {

    private String publishedAt;

    private String channelTitle;

    private String channelId;

    private String duration;

    private String category;

    private String description;

    private String imageUrl;

    private String title;

    private String videoId;

    private Date date;

    @Override
    public int compareTo(VideoModel o) {
        return date.compareTo(o.getDate());
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getDuration() {
        return duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoId() {
        return videoId;
    }

    public Date getDate() {
        return date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.publishedAt);
        dest.writeString(this.channelTitle);
        dest.writeString(this.channelId);
        dest.writeString(this.duration);
        dest.writeString(this.category);
        dest.writeString(this.description);
        dest.writeString(this.imageUrl);
        dest.writeString(this.title);
        dest.writeString(this.videoId);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
    }

    public VideoModel() {
    }

    protected VideoModel(Parcel in) {
        this.publishedAt = in.readString();
        this.channelTitle = in.readString();
        this.channelId = in.readString();
        this.duration = in.readString();
        this.category = in.readString();
        this.description = in.readString();
        this.imageUrl = in.readString();
        this.title = in.readString();
        this.videoId = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Creator<VideoModel> CREATOR = new Creator<VideoModel>() {
        @Override
        public VideoModel createFromParcel(Parcel source) {
            return new VideoModel(source);
        }

        @Override
        public VideoModel[] newArray(int size) {
            return new VideoModel[size];
        }
    };
}
