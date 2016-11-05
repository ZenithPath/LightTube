package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.CommentsDataManagerImp;
import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.ThreadCommentsWrapper;

import rx.Observable;

public class RetrieveCommentsUseCase extends UseCase<ThreadCommentsWrapper> {

    private ICommentsDataManager commentsDataManager;

    private String videoId;

    private String order;

    private int page;

    public RetrieveCommentsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ICommentsDataManager dataManager) {
        super(subscribeOn, observeOn);
        this.commentsDataManager = dataManager;
    }

    @Override
    protected Observable<ThreadCommentsWrapper> getUseCaseObservable() {
        return commentsDataManager.getCommentList(videoId, order, page);
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setOrder(@CommentsDataManagerImp.CommentsOrders String order) {
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
