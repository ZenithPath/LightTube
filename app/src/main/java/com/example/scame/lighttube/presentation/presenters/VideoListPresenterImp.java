package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.domain.usecases.ContentDetailsUseCase;
import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.VideoListUseCase;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

public class VideoListPresenterImp<V extends IVideoListPresenter.VideoListView>
                                                implements IVideoListPresenter<V> {

    private static final int FIRST_PAGE = 0;

    private V view;

    private int page;

    private VideoListUseCase videosUseCase;

    private ContentDetailsUseCase contentUseCase;

    public VideoListPresenterImp(VideoListUseCase videosUseCase, ContentDetailsUseCase contentUseCase) {
        this.videosUseCase = videosUseCase;
        this.contentUseCase = contentUseCase;
    }

    @Override
    public void setView(V view) {
        this.view = view;
    }

    @Override
    public void fetchVideos(int page) {
        this.page = page;
        videosUseCase.setPage(page);
        videosUseCase.execute(new VideoListSubscriber());
    }

    @Override
    public void resume() { }

    @Override
    public void pause() { }

    @Override
    public void destroy() { }

    private final class VideoListSubscriber extends DefaultSubscriber<List<VideoModel>> {

        @Override
        public void onNext(List<VideoModel> list) {
            super.onNext(list);

            contentUseCase.setVideoModels(list);
            contentUseCase.execute(new ContentDetailsSubscriber());
        }
    }


    private final class ContentDetailsSubscriber extends DefaultSubscriber<List<VideoModel>> {

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
