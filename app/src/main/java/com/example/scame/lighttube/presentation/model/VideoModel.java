package com.example.scame.lighttube.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoModel implements Parcelable, ModelMarker {

    private String duration;

    private String imageUrl;

    private String title;

    private String id;

    public VideoModel() { }

    public VideoModel(String imageUrl, String title, String id) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.id = id;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.duration);
        dest.writeString(this.imageUrl);
        dest.writeString(this.title);
        dest.writeString(this.id);
    }

    protected VideoModel(Parcel in) {
        this.duration = in.readString();
        this.imageUrl = in.readString();
        this.title = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<VideoModel> CREATOR = new Parcelable.Creator<VideoModel>() {
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
