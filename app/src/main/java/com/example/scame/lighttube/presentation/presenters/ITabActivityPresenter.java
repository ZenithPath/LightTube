package com.example.scame.lighttube.presentation.presenters;

public interface ITabActivityPresenter<T> extends Presenter<T> {

    interface ITabActivityView {

        void setBottomBarItems(boolean isSignedIn);
    }

    void checkLogin();
}
