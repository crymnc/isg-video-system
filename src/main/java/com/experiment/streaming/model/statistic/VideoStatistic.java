package com.experiment.streaming.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoStatistic extends Statistic{
    private Long videoId;
    private Double watchDuration;
    private Double remainingTime;
}
