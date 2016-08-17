package com.example.scame.lighttubex.presentation.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scame.lighttubex.R;
import com.example.scame.lighttubex.presentation.activities.TabActivity;
import com.example.scame.lighttubex.presentation.presenters.ISignInPresenter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInFragment extends BaseFragment implements ISignInPresenter.SignInView,
                                    GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 1000;

    private static final String SIGN_IN_TAG = "sign_in_log";

    public static final String YOUTUBE_SCOPE = "https://www.googleapis.com/auth/youtube";
    public static final String YOUTUBE_UPLOAD_SCOPE = "https://www.googleapis.com/auth/youtube.upload";

    private View fragmentView;

    private SignUpListener signUpListener;

    @BindView(R.id.status_tv) TextView statusTextView;

    @Inject
    ISignInPresenter<ISignInPresenter.SignInView> signInPresenter;

    @Inject GoogleApiClient.Builder googleApiClientBuilder;
    private GoogleApiClient googleApiClient;

    public interface SignUpListener {
        void signedIn();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SignUpListener) {
            signUpListener = (SignUpListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inject();
        signInPresenter.setView(this);

        googleApiClient = googleApiClientBuilder
                .enableAutoManage(getActivity(), this)
                .build();
        googleApiClient.connect();
    }

    private void inject() {
        if (getActivity() instanceof TabActivity) {
            TabActivity tabActivity = (TabActivity) getActivity();
            tabActivity.getSignInComponent().inject(this);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(SIGN_IN_TAG, "onConnectionFailed");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.signin_fragment, container, false);

        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @OnClick(R.id.sign_in_button)
    public void signInClick() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            signInPresenter.handleSignInResult(result);
            //signUpListener.signedIn();
        }
    }

    @OnClick(R.id.sign_out_button)
    public void signOutClick() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                status -> signInPresenter.signOutClick(status));
    }

    @Override
    public void updateUI(Boolean signedIn) {
        if (signedIn) {
            fragmentView.findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            fragmentView.findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        } else {
            statusTextView.setText(R.string.signed_out);

            fragmentView.findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            fragmentView.findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }
    }

    @Override
    public void setStatusTextView(String serverAuthCode) {
        statusTextView.setText(getString(R.string.signed_in_fmt, serverAuthCode));
    }

    @Override
    public void onResume() {
        super.onResume();

        signInPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();

        signInPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();

        signInPresenter.destroy();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_LONG).show();
    }
}
