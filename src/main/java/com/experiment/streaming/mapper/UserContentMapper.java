package com.experiment.streaming.mapper;

import com.experiment.streaming.entity.ContentEntity;
import com.experiment.streaming.entity.statistic.VideoStatisticEntity;
import com.experiment.streaming.model.UserContent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserContentMapper {

    public static UserContent toModel(ContentEntity contentEntity, List<VideoStatisticEntity> videoStatisticEntities){
        if(contentEntity == null)
            return null;
        UserContent content = new UserContent();
        content.setId(contentEntity.getId());
        content.setName(contentEntity.getName());
        content.setDescription(contentEntity.getDescription());
        content.setContentTypeId(contentEntity.getContentType().getId());
        content.setDuration(contentEntity.getDuration());
        content.setPath(contentEntity.getPath());
        content.setShortName(contentEntity.getShortName());
        content.setTitle(contentEntity.getTitle());
        content.setDiscontinueDate(contentEntity.getDiscontinueDate());
        content.setCompanyId(contentEntity.getCompany().getId());
        content.setCompanyName(contentEntity.getCompany().getName());
        videoStatisticEntities.stream()
                .filter(videoStatisticEntity -> videoStatisticEntity.getVideo().getId().equals(contentEntity.getId()))
                .max(Comparator.comparing(VideoStatisticEntity::getWatchDuration))
                .ifPresent(videoStatisticEntity -> {
                    if(videoStatisticEntity.getIsContentFinished())
                        content.setWatched(true);
                    BigDecimal watchDuration = new BigDecimal(videoStatisticEntity.getWatchDuration());
                    BigDecimal duration = new BigDecimal(contentEntity.getDuration());
                    content.setWatchedRate(watchDuration.divide(duration,RoundingMode.HALF_EVEN).setScale(1, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100)).intValue());
                });
        return content;
    }

    public static List<UserContent> toModelList(List<ContentEntity> contentEntities, List<VideoStatisticEntity> videoStatisticEntities){
        return contentEntities.stream().map(contentEntity -> toModel(contentEntity,videoStatisticEntities)).collect(Collectors.toList());
    }

}
