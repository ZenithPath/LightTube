package com.example.scame.lighttube.presentation.di.modules;

import android.app.Activity;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.repository.IAccountDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.SignInCheckUseCase;
import com.example.scame.lighttube.domain.usecases.SignInUseCase;
import com.example.scame.lighttube.domain.usecases.SignOutUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.fragments.SignInFragment;
import com.example.scame.lighttube.presentation.presenters.ISignInPresenter;
import com.example.scame.lighttube.presentation.presenters.SignInPresenterImp;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import dagger.Module;
import dagger.Provides;

@Module
public class SignInModule {

    private Activity activity;

    public SignInModule(Activity activity) {
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

    @PerActivity
    @Provides
    SignInUseCase provideSignInUseCase(IAccountDataManager dataManager,
                                       ObserveOn observeOn, SubscribeOn subscribeOn) {
        return new SignInUseCase(dataManager, subscribeOn, observeOn);
    }

    @PerActivity
    @Provides
    SignOutUseCase provideSignOutUseCase(IAccountDataManager dataManager,
                                         ObserveOn observeOn, SubscribeOn subscribeOn) {
        return new SignOutUseCase(dataManager, subscribeOn, observeOn);
    }

    @PerActivity
    @Provides
    SignInCheckUseCase provideSignInCheckUseCase(IAccountDataManager dataManager,
                                                 ObserveOn observeOn, SubscribeOn subscribeOn) {
        return new SignInCheckUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    ISignInPresenter<ISignInPresenter.SignInView> provideSignInPresenter(SignInUseCase signInUseCase,
                                                                         SignOutUseCase signOutUseCase,
                                                                         SignInCheckUseCase signInCheckUseCase) {
        return new SignInPresenterImp<>(signInUseCase, signOutUseCase, signInCheckUseCase);
    }
}
