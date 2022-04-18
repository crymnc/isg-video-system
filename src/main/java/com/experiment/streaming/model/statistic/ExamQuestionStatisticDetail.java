package com.experiment.streaming.model.statistic;

import com.experiment.streaming.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamQuestionStatisticDetail extends BaseModel {
    @NotEmpty
    private String question;
    @NotNull
    private Boolean isMultipleChoice;
    private String title;
    private Integer order;
    private List<ExamAnswerStatisticDetail> answers = new ArrayList<>();
}
