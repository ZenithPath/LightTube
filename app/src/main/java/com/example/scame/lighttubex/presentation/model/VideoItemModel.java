package com.example.scame.lighttubex.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoItemModel implements Parcelable {

    private String thumbnails;

    private String title;

    private String id;

    public VideoItemModel() { }

    public VideoItemModel(String thumbnails, String title, String id) {
        this.thumbnails = thumbnails;
        this.title = title;
        this.id = id;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumbnails);
        dest.writeString(this.title);
        dest.writeString(this.id);
    }

    protected VideoItemModel(Parcel in) {
        this.thumbnails = in.readString();
        this.title = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<VideoItemModel> CREATOR = new Parcelable.Creator<VideoItemModel>() {
        @Override
        public VideoItemModel createFromParcel(Parcel source) {
            return new VideoItemModel(source);
        }

        @Override
        public VideoItemModel[] newArray(int size) {
            return new VideoItemModel[size];
        }
    };
}
