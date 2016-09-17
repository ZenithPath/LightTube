package com.example.scame.lighttube.presentation.model;

import android.os.Parcel;

import com.google.android.gms.common.api.Scope;

import java.util.Set;

public class UserModel implements ModelMarker {

    private String userName;

    private String email;

    private String userId;

    private String photoUrl;

    private Set<Scope> grantedScopes;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setGrantedScopes(Set<Scope> grantedScopes) {
        this.grantedScopes = grantedScopes;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Set<Scope> getGrantedScopes() {
        return grantedScopes;
    }

    @Override
    public String toString() {
        return "User model details\n" +
                "user name: " + userName + "\n" +
                "email: " + email + "\n" +
                "photoUrl: " + photoUrl + "\n" +
                "grantedScopes" + grantedScopes + "\n";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.email);
        dest.writeString(this.userId);
        dest.writeString(this.photoUrl);
    }

    public UserModel() {
    }

    protected UserModel(Parcel in) {
        this.userName = in.readString();
        this.email = in.readString();
        this.userId = in.readString();
        this.photoUrl = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
