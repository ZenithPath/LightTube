package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.CommentsRepository;
import com.example.scame.lighttube.data.repository.CommentsRepositoryImp;
import com.example.scame.lighttube.data.repository.StatisticsRepository;
import com.example.scame.lighttube.data.repository.UserChannelRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.MergedCommentsModel;

import rx.Observable;
import rx.schedulers.Schedulers;

public class FooterInitializationUseCase extends UseCase<MergedCommentsModel> {

    private CommentsRepository commentsRepository;

    private StatisticsRepository statisticsDataManager;

    private UserChannelRepository userChannelDataManager;

    private SubscribeOn subscribeOn;

    private String videoId;

    private int page;

    private String order;

    public FooterInitializationUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, CommentsRepository commentsRepository,
                                       StatisticsRepository statsDataManager, UserChannelRepository userChannelDataManager) {
        super(subscribeOn, observeOn);

        this.commentsRepository = commentsRepository;
        this.statisticsDataManager = statsDataManager;
        this.userChannelDataManager = userChannelDataManager;
        this.subscribeOn = subscribeOn;
    }

    // TODO: should go into repository
    @Override
    protected Observable<MergedCommentsModel> getUseCaseObservable() {
        return Observable.zip(commentsRepository.getCommentList(videoId, order, page).subscribeOn(Schedulers.computation()),
                statisticsDataManager.getVideoStatistics(videoId).subscribeOn(subscribeOn.getScheduler()),
                userChannelDataManager.getUserChannelUrl().subscribeOn(subscribeOn.getScheduler()),
                        MergedCommentsModel::new);
    }

    public void setOrder(@CommentsRepositoryImp.CommentsOrders String order) {
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
