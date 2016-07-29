package com.example.scame.lighttubex.presentation.presenters;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Result;

public interface ISignInPresenter<V> extends Presenter<V> {

    interface SignInView {

        void updateUI(Boolean signedIn);

        void setStatusTextView(String serverAuthCode);

        void showError(String error);
    }


    void handleSignInResult(GoogleSignInResult result);

    void signOutClick(Result result);
}
