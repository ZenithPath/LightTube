package com.example.scame.lighttubex.presentation.presenters;

import com.example.scame.lighttubex.PrivateValues;
import com.example.scame.lighttubex.data.enteties.TokenEntity;
import com.example.scame.lighttubex.data.repository.SharedPrefsManager;
import com.example.scame.lighttubex.data.rest.TokenApi;
import com.example.scame.lighttubex.presentation.LightTubeApp;
import com.example.scame.lighttubex.presentation.di.PerActivity;
import com.example.scame.lighttubex.presentation.view.SignInView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Result;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@PerActivity
public class SignInPresenter<V extends SignInView> implements Presenter<V> {

    private SharedPrefsManager sharedPrefsManager;
    private Retrofit retrofit;

    private V signInView;

    @Inject
    public SignInPresenter() {
        sharedPrefsManager = LightTubeApp.getAppComponent().getSharedPrefsManager();
        retrofit = LightTubeApp.getAppComponent().getRetrofit();
    }

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

            // refactor this part
            fetchToken(acct.getServerAuthCode());

            signInView.setStatusTextView(acct.getServerAuthCode());
            signInView.updateUI(true);
        } else {
            signInView.updateUI(false);
        }
    }

    private void fetchToken(String serverAuthCode) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", PrivateValues.CLIENT_ID);
        params.put("client_secret", PrivateValues.SECRET_KEY);
        params.put("redirect_uri", "");
        params.put("code", serverAuthCode);

        TokenApi tokenApi = retrofit.create(TokenApi.class);
        tokenApi.getAccessToken(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::saveToken);
    }

    private void saveToken(TokenEntity tokenEntity) {
        sharedPrefsManager.saveToken(tokenEntity);
    }

    public void signOutClick(Result result) {
        // TODO: add sign-out effect logic

        signInView.updateUI(false);
    }
}
