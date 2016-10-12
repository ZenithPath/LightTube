package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import rx.Observable;

public class PostThreadCommentUseCase extends UseCase<ThreadCommentModel> {

    private ICommentsDataManager dataManager;

    private String commentText;

    private String videoId;

    public PostThreadCommentUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ICommentsDataManager dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<ThreadCommentModel> getUseCaseObservable() {
        return dataManager.postThreadComment(commentText, videoId);
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
