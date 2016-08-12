package com.example.scame.lighttubex.presentation.presenters;


import com.example.scame.lighttubex.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttubex.domain.usecases.VideoListUseCase;
import com.example.scame.lighttubex.presentation.model.VideoItemModel;

import java.util.List;

public class VideoListPresenterImp<V extends IVideoListPresenter.VideoListView>
                                                implements IVideoListPresenter<V> {

    private static final int FIRST_PAGE = 0;

    private V view;

    private int page;

    private VideoListUseCase useCase;

    public VideoListPresenterImp(VideoListUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void setView(V view) {
        this.view = view;
    }

    @Override
    public void fetchVideos(int page, List<VideoItemModel> savedItems) {

        if (savedItems == null || savedItems.isEmpty()) {
            this.page = page;
            useCase.setPage(page);
            useCase.execute(new VideoListSubscriber());
        } else {
            view.initializeAdapter(savedItems);
        }
    }

    @Override
    public void resume() { }

    @Override
    public void pause() { }

    @Override
    public void destroy() { }

    private final class VideoListSubscriber extends DefaultSubscriber<List<VideoItemModel>> {

        @Override
        public void onNext(List<VideoItemModel> list) {
            super.onNext(list);

            if (page == FIRST_PAGE) {
                view.initializeAdapter(list);
            } else {
                view.updateAdapter(list);
            }
        }
    }
}
