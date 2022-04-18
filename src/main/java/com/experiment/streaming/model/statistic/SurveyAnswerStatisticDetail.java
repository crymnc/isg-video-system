package com.experiment.streaming.model.statistic;

import com.experiment.streaming.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswerStatisticDetail extends BaseModel {
    private Integer order;
    @NotEmpty
    private String answerText;
    private Long questionId;
}
