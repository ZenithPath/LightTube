package com.example.scame.lighttubex.presentation.presenters;


import com.example.scame.lighttubex.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttubex.domain.usecases.VideoListUseCase;
import com.example.scame.lighttubex.presentation.model.VideoItemModel;

import java.util.List;

public class VideoListPresenterImp<V extends IVideoListPresenter.VideoListView>
                                                implements IVideoListPresenter<V> {

    private V view;

    private VideoListUseCase useCase;

    public VideoListPresenterImp(VideoListUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void setView(V view) {
        this.view = view;
    }

    @Override
    public void fetchVideos() {
        useCase.execute(new VideoListSubscriber());
    }

    private final class VideoListSubscriber extends DefaultSubscriber<List<VideoItemModel>> {

        @Override
        public void onNext(List<VideoItemModel> list) {
            super.onNext(list);

            view.populateAdapter(list);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
        }

        @Override
        public void onCompleted() {
            super.onCompleted();
        }
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
}
