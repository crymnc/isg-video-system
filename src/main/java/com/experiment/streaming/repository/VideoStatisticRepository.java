package com.experiment.streaming.repository;

import com.experiment.streaming.entity.ContentEntity;
import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.statistic.VideoStatisticEntity;
import com.experiment.streaming.repository.base.EntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoStatisticRepository extends EntityRepository<VideoStatisticEntity> {

    Optional<VideoStatisticEntity> findByVideo(ContentEntity video);

    List<VideoStatisticEntity> findByVideoAndUser(ContentEntity video, UserEntity user);
    List<VideoStatisticEntity> findByUser(UserEntity user);
}
