package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.CommentsRepository;
import com.example.scame.lighttube.data.repository.CommentsRepositoryImp;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.ThreadCommentsWrapper;

import rx.Observable;

public class GetCommentsUseCase extends UseCase<ThreadCommentsWrapper> {

    private CommentsRepository commentsRepository;

    private String videoId;

    private String order;

    private int page;

    public GetCommentsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, CommentsRepository dataManager) {
        super(subscribeOn, observeOn);
        this.commentsRepository = dataManager;
    }

    @Override
    protected Observable<ThreadCommentsWrapper> getUseCaseObservable() {
        return commentsRepository.getCommentList(videoId, order, page);
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setOrder(@CommentsRepositoryImp.CommentsOrders String order) {
        this.order = order;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getOrder() {
        return order;
    }

    public int getPage() {
        return page;
    }
}
