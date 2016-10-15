package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import rx.Observable;

public class MarkAsSpamUseCase extends UseCase<Void> {

    private ICommentsDataManager dataManager;

    private String commentId;

    public MarkAsSpamUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ICommentsDataManager dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<Void> getUseCaseObservable() {
        return dataManager.markAsSpam(commentId);
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
