package com.example.scame.lighttubex.presentation.di;

/**
 * interface representing a contract for clients that contain a component for dependency injection
 */
public interface HasComponent<C> {
    C getComponent();
}
