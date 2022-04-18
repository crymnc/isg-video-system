package com.experiment.streaming.model.statistic;

import com.experiment.streaming.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyStatistic {
    private Long surveyId;
    private User user;
    private String surveyStatus;
}
