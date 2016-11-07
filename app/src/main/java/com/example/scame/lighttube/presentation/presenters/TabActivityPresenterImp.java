package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.CheckLoginUseCase;

public class TabActivityPresenterImp<T extends TabActivityPresenter.ITabActivityView>
                                            implements TabActivityPresenter<T> {


    private CheckLoginUseCase useCase;

    private SubscriptionsHandler subscriptionsHandler;

    private T view;

    public TabActivityPresenterImp(CheckLoginUseCase useCase, SubscriptionsHandler subscriptionsHandler) {
        this.subscriptionsHandler = subscriptionsHandler;
        this.useCase = useCase;
    }

    @Override
    public void checkLogin() {
        useCase.execute(new SignInCheckSubscriber());
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        subscriptionsHandler.unsubscribe();
        view = null;
    }

    private final class SignInCheckSubscriber extends DefaultSubscriber<Boolean> {

        @Override
        public void onNext(Boolean isSignIn) {
            super.onNext(isSignIn);

            if (view != null) {
                view.setBottomBarItems(isSignIn);
            }
        }
    }
}
