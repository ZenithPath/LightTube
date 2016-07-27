package com.example.scame.lighttubex.presentation.presenters;

import com.example.scame.lighttubex.presentation.di.PerActivity;
import com.example.scame.lighttubex.presentation.view.SignInView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Result;

import javax.inject.Inject;

@PerActivity
public class SignInPresenter<V extends SignInView> implements Presenter<V> {

    private V signInView;

    @Inject
    public SignInPresenter() { }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setView(V signInView) {
        this.signInView = signInView;
    }

    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            // TODO: implement caching
            signInView.setStatusTextView(acct.getServerAuthCode());
            signInView.updateUI(true);
        } else {
            signInView.updateUI(false);
        }
    }

    public void signOutClick(Result result) {
        // TODO: add sign-out effect logic

        signInView.updateUI(false);
    }
}
