package com.experiment.streaming.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamStatisticDetail {
    private List<VideoStatistic> videoStatistics;
    private ExamStatistic examStatistic;
    private List<ExamQuestionStatisticDetail> questions;
}
