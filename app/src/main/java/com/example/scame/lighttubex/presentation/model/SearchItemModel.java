package com.example.scame.lighttubex.presentation.model;


import android.os.Parcel;
import android.os.Parcelable;

public class SearchItemModel implements Parcelable {

    private String imageUrl;

    private String title;

    private String id;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
        dest.writeString(this.title);
        dest.writeString(this.id);
    }

    public SearchItemModel() {
    }

    protected SearchItemModel(Parcel in) {
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
