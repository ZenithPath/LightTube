package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.RepliesWrapper;

import rx.Observable;

public class RetrieveRepliesUseCase extends UseCase<RepliesWrapper> {

    private ICommentsDataManager dataManager;

    private String parentId;

    private int page;

    public RetrieveRepliesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ICommentsDataManager dataManager) {
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
