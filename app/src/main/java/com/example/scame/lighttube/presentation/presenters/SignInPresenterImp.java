package com.example.scame.lighttube.presentation.presenters;

import com.example.scame.lighttube.data.entities.TokenEntity;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.SignInCheckUseCase;
import com.example.scame.lighttube.domain.usecases.SignInUseCase;
import com.example.scame.lighttube.domain.usecases.SignOutUseCase;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Result;

public class SignInPresenterImp<V extends ISignInPresenter.SignInView> implements ISignInPresenter<V> {

    private SignInUseCase signInUseCase;

    private SignOutUseCase signOutUseCase;

    private SignInCheckUseCase signInCheckUseCase;

    private V signInView;

    public SignInPresenterImp(SignInUseCase signInUseCase, SignOutUseCase signOutUseCase,
                              SignInCheckUseCase signInCheckUseCase) {

        this.signInUseCase = signInUseCase;
        this.signOutUseCase = signOutUseCase;
        this.signInCheckUseCase = signInCheckUseCase;
    }

    @Override
    public void setView(V signInView) {
        this.signInView = signInView;
    }

    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            signInUseCase.setServerAuthCode(acct.getServerAuthCode());
            signInUseCase.execute(new SignInSubscriber());

            signInView.updateUI(true);
        } else {
            signInView.updateUI(false);
        }
    }

    public void signOutClick(Result result) {
        signOutUseCase.execute(new SignOutSubscriber());
        signInView.updateUI(false);
    }

    @Override
    public void isSignedIn() {
        signInCheckUseCase.execute(new SignInCheckSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        signInUseCase.unsubscribe();
        signOutUseCase.unsubscribe();
        signInCheckUseCase.unsubscribe();
        signInView = null;
    }

    private final class SignOutSubscriber extends DefaultSubscriber<Void> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            signInView.showError(e.toString());
        }

        @Override
        public void onCompleted() {
            super.onCompleted();

            signInView.signOut();
            signInView.setStatusTextView("signed out!");
        }
    }

    private final class SignInSubscriber extends DefaultSubscriber<TokenEntity> {

        @Override
        public void onCompleted() {
            super.onCompleted();

            signInView.signIn();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            signInView.showError(e.getMessage());
        }

        @Override
        public void onNext(TokenEntity tokenEntity) {
            super.onNext(tokenEntity);

            signInView.setStatusTextView(tokenEntity.getAccessToken());
        }
    }

    private final class SignInCheckSubscriber extends DefaultSubscriber<Boolean> {

        @Override
        public void onNext(Boolean isSignedIn) {
            super.onNext(isSignedIn);

            signInView.updateUI(isSignedIn);
        }
    }
}
