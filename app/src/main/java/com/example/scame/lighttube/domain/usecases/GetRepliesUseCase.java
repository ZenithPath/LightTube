package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.CommentsRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.RepliesWrapper;

import rx.Observable;

public class GetRepliesUseCase extends UseCase<RepliesWrapper> {

    private CommentsRepository dataManager;

    private String parentId;

    private int page;

    public GetRepliesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, CommentsRepository dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<RepliesWrapper> getUseCaseObservable() {
        return dataManager.getReplyList(parentId, page);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
    }

    public int getPage() {
        return page;
    }
}
