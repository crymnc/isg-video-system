package com.experiment.streaming.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyStatisticDetail {
    private SurveyStatistic surveyStatistic;
    private List<SurveyQuestionStatisticDetail> questions;
}
