package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.SignInCheckUseCase;

public class TabActivityPresenterImp<T extends ITabActivityPresenter.ITabActivityView>
                                            implements ITabActivityPresenter<T> {


    private SignInCheckUseCase useCase;

    private T view;

    public TabActivityPresenterImp(SignInCheckUseCase useCase) {
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

    }

    private final class SignInCheckSubscriber extends DefaultSubscriber<Boolean> {

        @Override
        public void onNext(Boolean isSignIn) {
            super.onNext(isSignIn);

            view.setBottomBarItems(isSignIn);
        }
    }
}
