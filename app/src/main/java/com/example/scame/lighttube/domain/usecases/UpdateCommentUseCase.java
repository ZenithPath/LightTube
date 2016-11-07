package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.CommentsRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import rx.Observable;

public class UpdateCommentUseCase extends UseCase<ThreadCommentModel> {

    private CommentsRepository dataManager;

    private String updatedText;

    private String commentId;

    public UpdateCommentUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, CommentsRepository dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<ThreadCommentModel> getUseCaseObservable() {
        return dataManager.updateThreadComment(updatedText, commentId);
    }

    public void setUpdatedText(String updatedText) {
        this.updatedText = updatedText;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUpdatedText() {
        return updatedText;
    }

    public String getCommentId() {
        return commentId;
    }
}
