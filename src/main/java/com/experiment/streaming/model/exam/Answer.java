package com.experiment.streaming.model.exam;

import com.experiment.streaming.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseModel {
    private Integer order;
    @NotEmpty
    private String answer;
    private Boolean isRightAnswer;
    private Long questionId;
}
