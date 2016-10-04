package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.CommentListModel;

import rx.Observable;

public interface ICommentsDataManager {

    Observable<CommentListModel> getCommentList(String videoId);
}
