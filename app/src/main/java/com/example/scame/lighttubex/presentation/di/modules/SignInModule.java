package com.example.scame.lighttubex.presentation.di.modules;

import com.example.scame.lighttubex.PrivateValues;
import com.example.scame.lighttubex.presentation.activities.SignInActivity;
import com.example.scame.lighttubex.presentation.di.PerActivity;
import com.example.scame.lighttubex.presentation.fragments.SignInFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import dagger.Module;
import dagger.Provides;

@Module
public class SignInModule {

    private SignInActivity activity;

    public SignInModule(SignInActivity activity) {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    GoogleApiClient.Builder provideGoogleApiClientBuilder(GoogleSignInOptions gso) {
        return new GoogleApiClient.Builder(activity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso);
    }

    @PerActivity
    @Provides
    GoogleSignInOptions provideGoogleSignInOptions() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(SignInFragment.YOUTUBE_SCOPE),
                        new Scope(SignInFragment.YOUTUBE_UPLOAD_SCOPE))
                .requestServerAuthCode(PrivateValues.CLIENT_ID, true)
                .requestEmail()
                .build();
    }
}
