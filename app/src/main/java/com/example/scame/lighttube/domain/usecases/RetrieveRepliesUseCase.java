package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.ReplyModel;

import java.util.List;

import rx.Observable;

public class RetrieveRepliesUseCase extends UseCase<List<ReplyModel>> {

    private ICommentsDataManager dataManager;

    private String parentId;

    public RetrieveRepliesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ICommentsDataManager dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<List<ReplyModel>> getUseCaseObservable() {
        return dataManager.getReplyList(parentId);
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
    }
}
