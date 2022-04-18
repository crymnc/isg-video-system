package com.experiment.streaming.service;

import com.experiment.streaming.entity.ContentEntity;
import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.entity.statistic.VideoStatisticEntity;
import com.experiment.streaming.repository.VideoStatisticRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoStatisticService {
    private final VideoStatisticRepository videoStatisticRepository;

    public VideoStatisticService(VideoStatisticRepository videoStatisticRepository){
        this.videoStatisticRepository = videoStatisticRepository;
    }

    public Optional<VideoStatisticEntity> getVideoStatisticByVideoId(Long videoId){
        ContentEntity contentEntity = new ContentEntity();
        contentEntity.setId(videoId);
        return videoStatisticRepository.findByVideo(contentEntity);
    }

    public List<VideoStatisticEntity> getVideoStatisticByVideoIdAndUserId(Long videoId, Long userId){
        ContentEntity contentEntity = new ContentEntity();
        contentEntity.setId(videoId);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return videoStatisticRepository.findByVideoAndUser(contentEntity, userEntity);
    }

    public List<VideoStatisticEntity> getVideoStatisticByUserId(Long userId){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return videoStatisticRepository.findByUser(userEntity);
    }
}
