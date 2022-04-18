package com.experiment.streaming.model.exam;

import com.experiment.streaming.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result extends BaseModel {
    private Long userId;
    private Long examId;
    private Long questionId;
    private Long answerId;
    private String answerText;
    private Boolean isCorrect;
}
