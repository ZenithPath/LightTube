package com.example.scame.lighttube.presentation.model;


import android.os.Parcel;
import android.os.Parcelable;

public class ChannelModel implements Parcelable {

    private String channelId;

    private String imageUrl;

    private String title;

    public ChannelModel() { }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.channelId);
        dest.writeString(this.imageUrl);
        dest.writeString(this.title);
    }

    protected ChannelModel(Parcel in) {
        this.channelId = in.readString();
        this.imageUrl = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<ChannelModel> CREATOR = new Parcelable.Creator<ChannelModel>() {
        @Override
        public ChannelModel createFromParcel(Parcel source) {
            return new ChannelModel(source);
        }

        @Override
        public ChannelModel[] newArray(int size) {
            return new ChannelModel[size];
        }
    };
}
