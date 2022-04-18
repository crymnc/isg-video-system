package com.experiment.streaming.mapper.statistic;

import com.experiment.streaming.entity.ContentEntity;
import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.statistic.VideoStatisticEntity;
import com.experiment.streaming.model.base.BaseModel;
import com.experiment.streaming.model.statistic.VideoStatistic;

import java.util.List;
import java.util.stream.Collectors;

public class VideoStatisticMapper extends BaseModel {

    public static VideoStatistic toModel(VideoStatisticEntity videoStatisticEntity){
        if(videoStatisticEntity == null)
            return null;
        VideoStatistic videoStatistic = new VideoStatistic();
        videoStatistic.setId(videoStatisticEntity.getId());
        videoStatistic.setVideoId(videoStatisticEntity.getVideo().getId());
        videoStatistic.setRemainingTime(videoStatisticEntity.getRemainingTime());
        videoStatistic.setWatchDuration(videoStatisticEntity.getWatchDuration());
        videoStatistic.setStartedAt(videoStatisticEntity.getStartedAt());
        videoStatistic.setFinishedAt(videoStatisticEntity.getFinishedAt());
        videoStatistic.setIsContentFinished(videoStatisticEntity.getIsContentFinished());
        videoStatistic.setUserId(videoStatisticEntity.getUser().getId());
        return videoStatistic;
    }

    public static VideoStatisticEntity toEntity(VideoStatistic videoStatistic){
        if(videoStatistic == null)
            return null;
        VideoStatisticEntity videoStatisticEntity = new VideoStatisticEntity();
        videoStatisticEntity.setId(videoStatistic.getId());

        videoStatisticEntity.setRemainingTime(videoStatistic.getRemainingTime());
        videoStatisticEntity.setWatchDuration(videoStatistic.getWatchDuration());
        videoStatisticEntity.setStartedAt(videoStatistic.getStartedAt());
        videoStatisticEntity.setFinishedAt(videoStatistic.getFinishedAt());
        videoStatisticEntity.setIsContentFinished(videoStatistic.getIsContentFinished());

        ContentEntity videoContentEntity = new ContentEntity();
        videoContentEntity.setId(videoStatistic.getVideoId());
        videoStatisticEntity.setVideo(videoContentEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(videoStatistic.getUserId());
        videoStatisticEntity.setUser(userEntity);
        return videoStatisticEntity;
    }

    public static List<VideoStatistic> toModelList(List<VideoStatisticEntity> entityList){
        return entityList.stream().map(entity -> toModel(entity)).collect(Collectors.toList());
    }

    public static List<VideoStatisticEntity> toEntityList(List<VideoStatistic> modelList){
        return modelList.stream().map(model -> toEntity(model)).collect(Collectors.toList());
    }
}
