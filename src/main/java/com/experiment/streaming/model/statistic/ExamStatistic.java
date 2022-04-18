package com.experiment.streaming.model.statistic;

import com.experiment.streaming.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamStatistic{
    private Long examId;
    private User user;
    private String examStatus;
    private String trueWrongRate;
    private Boolean isAssessed;
}
