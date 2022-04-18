package com.experiment.streaming.model.exam;

import com.experiment.streaming.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseModel {
    private Integer order;
    private String question;
    private Boolean isMultipleChoice;
    private String title;
    private Long examId;
}
