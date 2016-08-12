package com.example.scame.lighttubex.data.entities.videolist;

import com.google.gson.annotations.SerializedName;

public class ThumbnailsGroup {

    @SerializedName("default")
    private ThumbnailsEntity small;

    private ThumbnailsEntity medium;

    public void setMedium(ThumbnailsEntity medium) {
        this.medium = medium;
    }

    public ThumbnailsEntity getMedium() {
        return medium;
    }

    public void setSmall(ThumbnailsEntity small) {
        this.small = small;
    }

    public ThumbnailsEntity getSmall() {
        return small;
    }
}
