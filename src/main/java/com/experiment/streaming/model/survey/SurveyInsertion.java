package com.experiment.streaming.model.survey;

import com.experiment.streaming.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyInsertion extends BaseModel {
    @NotEmpty(message = "{survey.name.NotEmpty}")
    private String name;
    @NotEmpty(message = "{survey.title.NotEmpty}")
    private String title;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date finishDate;
    @NotNull(message = "{survey.companyId.NotNull}")
    private Long companyId;
    private Long contentId;
    @NotEmpty(message = "{survey.questions.NotEmpty}")
    private List<QuestionInsertion> questions = new ArrayList<>();

}
