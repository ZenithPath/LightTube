package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.CommentsDataManagerImp;
import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.data.repository.IStatisticsDataManager;
import com.example.scame.lighttube.data.repository.IUserChannelDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.MergedCommentsModel;

import rx.Observable;
import rx.schedulers.Schedulers;

public class FooterInitializationUseCase extends UseCase<MergedCommentsModel> {

    private ICommentsDataManager commentsDataManager;

    private IStatisticsDataManager statisticsDataManager;

    private IUserChannelDataManager userChannelDataManager;

    private String videoId;

    private int page;

    private String order;

    public FooterInitializationUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ICommentsDataManager commentsDataManager,
                                       IStatisticsDataManager statsDataManager, IUserChannelDataManager userChannelDataManager) {
        super(subscribeOn, observeOn);

        this.commentsDataManager = commentsDataManager;
        this.statisticsDataManager = statsDataManager;
        this.userChannelDataManager = userChannelDataManager;
    }

    @Override
    protected Observable<MergedCommentsModel> getUseCaseObservable() {
        return Observable.zip(commentsDataManager.getCommentList(videoId, order, page).subscribeOn(Schedulers.computation()),
                statisticsDataManager.getVideoStatistics(videoId).subscribeOn(Schedulers.computation()),
                userChannelDataManager.getUserChannelUrl().subscribeOn(Schedulers.computation()),
                        MergedCommentsModel::new);
    }

    public void setOrder(@CommentsDataManagerImp.CommentsOrders String order) {
        this.order = order;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public int getPage() {
        return page;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getOrder() {
        return order;
    }
}
