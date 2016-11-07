package com.example.scame.lighttube.presentation.presenters;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Result;

public interface LoginPresenter<V> extends Presenter<V> {

    interface LoginView {

        void signOut();

        void signIn();

        void updateUI(Boolean signedIn);

        void setStatusTextView(String serverAuthCode);

        void showError(String error);
    }


    void handleSignInResult(GoogleSignInResult result);

    void signOutClick(Result result);

    void isSignedIn();
}
