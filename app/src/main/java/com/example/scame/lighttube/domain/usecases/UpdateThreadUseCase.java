package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import rx.Observable;

public class UpdateThreadUseCase extends UseCase<ThreadCommentModel> {

    private ICommentsDataManager dataManager;

    private String updatedText;

    private String commentId;

    public UpdateThreadUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ICommentsDataManager dataManager) {
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
}
