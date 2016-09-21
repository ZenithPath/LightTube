package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.GridListUseCase;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

public class GridPresenterImp<V extends IGridPresenter.GridView> implements IGridPresenter<V> {

    private static final int FIRST_PAGE = 0;

    private V view;

    private int page;

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
    public void fetchVideos(String category, String duration, int page) {
        this.page = page;
        useCase.setCategory(category);
        useCase.setDuration(duration);
        useCase.setPage(page);

        useCase.execute(new GridSubscriber());
    }

    private final class GridSubscriber extends DefaultSubscriber<List<VideoModel>> {

        @Override
        public void onNext(List<VideoModel> videoModels) {
            super.onNext(videoModels);

            if (page == FIRST_PAGE) {
                view.initializeAdapter(videoModels);
            } else {
                view.updateAdapter(videoModels);
            }
        }
    }
}
