package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.domain.usecases.UseCase;

import java.util.Arrays;
import java.util.List;

public class SubscriptionsHandler {

    private List<UseCase> useCases;

    public SubscriptionsHandler(UseCase... useCases) {
        this.useCases = Arrays.asList(useCases);
    }

    public void unsubscribe() {
        for (UseCase useCase : useCases) {
            useCase.unsubscribe();
        }
    }
}
