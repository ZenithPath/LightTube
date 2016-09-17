package com.example.scame.lighttube.presentation.adapters;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.example.scame.lighttube.presentation.model.ModelMarker;

/**
 * serves as a marker for adapter's noConnectionView item
 * Parcelable interface required by savedInstanceState bundle
 */
@SuppressLint("ParcelCreator")
public class NoConnectionMarker implements ModelMarker {
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
