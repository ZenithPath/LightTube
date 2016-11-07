package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.CommentsRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import rx.Observable;

public class PostCommentUseCase extends UseCase<ThreadCommentModel> {

    private CommentsRepository dataManager;

    private String commentText;

    private String videoId;

    public PostCommentUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, CommentsRepository dataManager) {
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

    public String getCommentText() {
        return commentText;
    }

    public String getVideoId() {
        return videoId;
    }
}
