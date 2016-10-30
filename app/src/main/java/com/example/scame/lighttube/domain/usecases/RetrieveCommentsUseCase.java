package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.List;

import rx.Observable;

public class RetrieveCommentsUseCase extends UseCase<List<ThreadCommentModel>> {

    ICommentsDataManager dataManager;

    private String videoId;

    public RetrieveCommentsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ICommentsDataManager dataManager) {
        super(subscribeOn, observeOn);

        this.dataManager = dataManager;
    }

    @Override
    protected Observable<List<ThreadCommentModel>> getUseCaseObservable() {
        return dataManager.getCommentList(videoId);
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }
}
