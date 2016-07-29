package com.example.scame.lighttubex.presentation.presenters;

import com.example.scame.lighttubex.data.enteties.TokenEntity;
import com.example.scame.lighttubex.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttubex.domain.usecases.SignInUseCase;
import com.example.scame.lighttubex.presentation.di.PerActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Result;

import javax.inject.Inject;

@PerActivity
public class SignInPresenterImp<V extends ISignInPresenter.SignInView> implements ISignInPresenter<V> {

    private SignInUseCase signInUseCase;

    private V signInView;

    @Inject
    public SignInPresenterImp(SignInUseCase signInUseCase) {
        this.signInUseCase = signInUseCase;
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
        // TODO: implement sign-out logic

        signInView.updateUI(false);
    }

    private final class SignInSubscriber extends DefaultSubscriber<TokenEntity> {
        @Override
        public void onCompleted() {
            super.onCompleted();
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

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        signInUseCase.unsubscribe();
        signInView = null;
    }
}
