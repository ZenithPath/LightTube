package com.example.scame.lighttube.presentation.fragments;

import android.support.v4.app.Fragment;

import com.example.scame.lighttube.presentation.di.HasComponent;

public class BaseFragment extends Fragment {

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
