package com.experiment.streaming.entity.statistic;

import com.experiment.streaming.entity.ContentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "video_statistic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoStatisticEntity extends StatisticEntity{

    @ManyToOne
    @JoinColumn(name = "video_id")
    private ContentEntity video;

    @Column(name="watchDuration")
    private Double watchDuration;

    @Column(name="remainingTime")
    private Double remainingTime;
}
