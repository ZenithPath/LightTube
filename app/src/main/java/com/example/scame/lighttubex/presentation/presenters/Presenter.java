package com.example.scame.lighttubex.presentation.presenters;

public interface Presenter<V> {

    void setView(V view);

    void resume();

    void pause();

    void destroy();
}
