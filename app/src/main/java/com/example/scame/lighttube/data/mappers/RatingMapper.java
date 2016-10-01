package com.example.scame.lighttube.data.mappers;


import com.example.scame.lighttube.data.entities.rating.RatingEntity;
import com.example.scame.lighttube.presentation.model.RatingModel;

public class RatingMapper {

    public RatingModel convert(RatingEntity ratingEntity) {
        RatingModel ratingModel = new RatingModel();

        ratingModel.setRating(ratingEntity.getItems().get(0).getRating());
        ratingModel.setVideoId(ratingEntity.getItems().get(0).getVideoId());

        return ratingModel;
    }
}
