package com.example.scame.lighttubex.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.presentation.di.HasComponent;
import com.example.scame.lighttubex.presentation.di.components.ApplicationComponent;
import com.example.scame.lighttubex.presentation.di.components.DaggerSignInComponent;
import com.example.scame.lighttubex.presentation.di.components.SignInComponent;
import com.example.scame.lighttubex.presentation.di.modules.SignInModule;
import com.example.scame.lighttubex.presentation.fragments.SignInFragment;

public class SignInActivity extends BaseActivity implements SignInFragment.SignUpListener,
                                                    HasComponent<SignInComponent> {

    public static final String SIGN_IN_FRAG_TAG = "signInFragment";

    private SignInComponent signInComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        if (getSupportFragmentManager().findFragmentByTag(SIGN_IN_FRAG_TAG) == null) {
            replaceFragment(R.id.signin_activity_fl, new SignInFragment(), SIGN_IN_FRAG_TAG);
        }
    }


    @Override
    protected void inject(ApplicationComponent appComponent) {
        signInComponent = DaggerSignInComponent.builder()
                .applicationComponent(getAppComponent())
                .signInModule(new SignInModule(this))
                .build();

        signInComponent.inject(this);
    }

    @Override
    public void signedIn() {
        navigator.navigateToVideoList(this);
    }

    @Override
    public SignInComponent getComponent() {
        return signInComponent;
    }
}

