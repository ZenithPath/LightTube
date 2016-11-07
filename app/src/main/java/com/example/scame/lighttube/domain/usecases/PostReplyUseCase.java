package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.CommentsRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.ReplyModel;

import rx.Observable;

public class PostReplyUseCase extends UseCase<ReplyModel> {

    private CommentsRepository dataManager;

    private String parentId;

    private String replyText;

    public PostReplyUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, CommentsRepository dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<ReplyModel> getUseCaseObservable() {
        return dataManager.postReply(replyText, parentId);
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
    }

    public String getReplyText() {
        return replyText;
    }
}
