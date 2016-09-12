package com.example.scame.lighttube.presentation.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoInternetFragment extends BaseFragment {

    public InternetConnectionListener connectionListener;

    @BindView(R.id.no_internet_toolbar) Toolbar toolbar;

    public interface InternetConnectionListener {

        void retry();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof InternetConnectionListener) {
            connectionListener = (InternetConnectionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.no_internet_layout, container, false);

        ButterKnife.bind(this, fragmentView);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @OnClick(R.id.retry_btn)
    public void onRetryClick() {
        connectionListener.retry();
    }
}
