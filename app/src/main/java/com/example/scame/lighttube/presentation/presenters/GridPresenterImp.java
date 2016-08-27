package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.GridListUseCase;
import com.example.scame.lighttube.presentation.model.SearchItemModel;

import java.util.List;

public class GridPresenterImp<V extends IGridPresenter.GridView> implements IGridPresenter<V> {

    private V view;

    private GridListUseCase useCase;

    public GridPresenterImp(GridListUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void setView(V view) {
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

    @Override
    public void fetchVideos(String category, String duration) {
        useCase.setCategory(category);
        useCase.setDuration(duration);
        useCase.execute(new GridSubscriber());
    }

    private final class GridSubscriber extends DefaultSubscriber<List<SearchItemModel>> {

        @Override
        public void onNext(List<SearchItemModel> searchItems) {
            super.onNext(searchItems);

            view.populateAdapter(searchItems);
        }
    }
}
