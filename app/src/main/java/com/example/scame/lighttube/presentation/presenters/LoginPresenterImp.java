package com.example.scame.lighttube.presentation.presenters;

import com.example.scame.lighttube.data.entities.TokenEntity;
import com.example.scame.lighttube.domain.usecases.CheckLoginUseCase;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.SignInUseCase;
import com.example.scame.lighttube.domain.usecases.SignOutUseCase;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Result;

public class LoginPresenterImp<V extends LoginPresenter.LoginView> implements LoginPresenter<V> {

    private SignInUseCase signInUseCase;

    private SignOutUseCase signOutUseCase;

    private CheckLoginUseCase checkLoginUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private V view;

    public LoginPresenterImp(SignInUseCase signInUseCase, SignOutUseCase signOutUseCase,
                             CheckLoginUseCase checkLoginUseCase, SubscriptionsHandler subscriptionsHandler) {
        this.signInUseCase = signInUseCase;
        this.signOutUseCase = signOutUseCase;
        this.checkLoginUseCase = checkLoginUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void setView(V signInView) {
        this.view = signInView;
    }

    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            signInUseCase.setServerAuthCode(acct.getServerAuthCode());
            signInUseCase.execute(new SignInSubscriber());

            if (view != null) view.updateUI(true);
        } else {
            if (view != null) view.updateUI(false);
        }
    }

    public void signOutClick(Result result) {
        signOutUseCase.execute(new SignOutSubscriber());
        if (view != null) view.updateUI(false);
    }

    @Override
    public void isSignedIn() {
        checkLoginUseCase.execute(new SignInCheckSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        subscriptionsHandler.unsubscribe();
        view = null;
    }

    private final class SignOutSubscriber extends DefaultSubscriber<Void> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            if (view != null) {
                view.showError(e.toString());
            }
        }

        @Override
        public void onCompleted() {
            super.onCompleted();

            if (view != null) {
                view.signOut();
                view.setStatusTextView("signed out!");
            }
        }
    }

    private final class SignInSubscriber extends DefaultSubscriber<TokenEntity> {

        @Override
        public void onCompleted() {
            super.onCompleted();

            if (view != null) {
                view.signIn();
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            if (view != null) {
                view.showError(e.getMessage());
            }
        }

        @Override
        public void onNext(TokenEntity tokenEntity) {
            super.onNext(tokenEntity);

            if (view != null) {
                view.setStatusTextView(tokenEntity.getAccessToken());
            }
        }
    }

    private final class SignInCheckSubscriber extends DefaultSubscriber<Boolean> {

        @Override
        public void onNext(Boolean isSignedIn) {
            super.onNext(isSignedIn);

            if (view != null) {
                view.updateUI(isSignedIn);
            }
        }
    }
}
