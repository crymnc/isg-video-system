package com.experiment.streaming.model.exam;

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
public class QuestionInsertion extends BaseModel {
    @NotEmpty
    private String question;
    @NotNull
    private Boolean isMultipleChoice;
    private String title;
    private Integer order;
    private boolean hasImage;
    private String imagePath;
    private List<Answer> answers = new ArrayList<>();
}
