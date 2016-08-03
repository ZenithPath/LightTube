package com.example.scame.lighttubex.presentation.presenters;


import com.example.scame.lighttubex.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttubex.domain.usecases.VideoListUseCase;
import com.example.scame.lighttubex.presentation.model.VideoItemModel;

import java.util.List;

public class VideoListPresenterImp<V extends IVideoListPresenter.VideoListView>
                                                implements IVideoListPresenter<V> {

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
    public void fetchVideos(int page) {
        this.page = page;
        useCase.setPage(page);
        useCase.execute(new VideoListSubscriber());
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

            if (page == 0) {
                view.populateAdapter(list);
            } else {
                view.updateAdapter(list);
            }
        }
    }
}
