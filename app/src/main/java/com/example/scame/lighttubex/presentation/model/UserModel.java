package com.example.scame.lighttubex.presentation.model;

import com.google.android.gms.common.api.Scope;

import java.util.Set;

public class UserModel {

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
}
