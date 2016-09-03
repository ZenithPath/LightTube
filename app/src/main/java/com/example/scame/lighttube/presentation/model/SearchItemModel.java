package com.example.scame.lighttube.presentation.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class SearchItemModel implements Parcelable, Comparable<SearchItemModel> {

    private String publishedAt;

    private String imageUrl;

    private String title;

    private String id;

    private Date date;

    @Override
    public int compareTo(SearchItemModel o) {
        return date.compareTo(o.getDate());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
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

    public String getPublishedAt() {
        return publishedAt;
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
        dest.writeString(this.imageUrl);
        dest.writeString(this.title);
        dest.writeString(this.id);
    }

    public SearchItemModel() {
    }

    protected SearchItemModel(Parcel in) {
        this.publishedAt = in.readString();
        this.imageUrl = in.readString();
        this.title = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<SearchItemModel> CREATOR = new Parcelable.Creator<SearchItemModel>() {
        @Override
        public SearchItemModel createFromParcel(Parcel source) {
            return new SearchItemModel(source);
        }

        @Override
        public SearchItemModel[] newArray(int size) {
            return new SearchItemModel[size];
        }
    };
}
